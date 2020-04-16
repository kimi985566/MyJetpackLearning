package cn.yangchengyu.libcommon.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.yangchengyu.libcommon.R;
import cn.yangchengyu.libcommon.databinding.LayoutRefreshViewBinding;
import cn.yangchengyu.libcommon.view.EmptyView;

/**
 * Desc  : 基类列表Fragment
 * Author: Chengyu Yang
 * Date  : 2020/4/12
 */
public abstract class AbsListFragment<T, M extends AbsPageListViewModel<T>> extends Fragment
        implements OnRefreshListener, OnLoadMoreListener, LifecycleOwner {

    protected LayoutRefreshViewBinding binding;
    /**
     * recyclerview
     */
    protected RecyclerView recyclerView;
    /**
     * 刷新布局
     */
    protected SmartRefreshLayout refreshLayout;
    /**
     * 空页面
     */
    protected EmptyView emptyView;

    /**
     * adapter
     */
    protected PagedListAdapter<T, RecyclerView.ViewHolder> adapter;

    /**
     * ViewModel
     */
    protected M viewModel;
    /**
     * ItemDecoration
     */
    protected DividerItemDecoration decoration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutRefreshViewBinding.inflate(inflater, container, false);
        binding.getRoot().setFitsSystemWindows(true);

        recyclerView = binding.recyclerView;
        refreshLayout = binding.refreshLayout;
        emptyView = binding.emptyView;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);

        adapter = getAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(null);

        //默认给列表中的Item 一个 10dp的ItemDecoration
        if (getContext() != null) {
            decoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.list_divider);
            if (drawable != null) {
                decoration.setDrawable(drawable);
            }
            recyclerView.addItemDecoration(decoration);
        }

        genericViewModel();
    }

    private void genericViewModel() {
        //利用 子类传递的 泛型参数实例化出absViewModel 对象。
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();

        if (type != null) {
            Type[] arguments = type.getActualTypeArguments();
            if (arguments.length > 1) {
                Type argument = arguments[1];
                Class modelClazz = ((Class) argument).asSubclass(AbsPageListViewModel.class);
                viewModel = (M) ViewModelProviders.of(this).get(modelClazz);

                //触发页面初始化数据加载的逻辑
                if (viewModel.getPagedListData() != null) {
                    viewModel.getPagedListData().observe(getViewLifecycleOwner(), this::submitList);
                }

                //监听分页时有无更多数据,以决定是否关闭上拉加载的动画
                viewModel.getBoundaryPageData().observe(getViewLifecycleOwner(), this::finishRefresh);
            }
        }
    }

    public void submitList(PagedList<T> result) {
        //只有当新数据集合大于0 的时候，才调用adapter.submitList
        //否则可能会出现 页面----有数据----->被清空-----空布局
        if (result != null && result.size() > 0) {
            adapter.submitList(result);
        }

        finishRefresh(result != null && result.size() > 0);
    }

    public void finishRefresh(boolean hasData) {
        PagedList<T> currentList = adapter.getCurrentList();
        hasData = hasData || currentList != null && currentList.size() > 0;

        RefreshState state = refreshLayout.getState();
        if (state.isFooter && state.isOpening) {
            refreshLayout.finishLoadMore();
        } else if (state.isHeader && state.isOpening) {
            refreshLayout.finishRefresh();
        }

        if (hasData) {
            emptyView.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 因而 我们在 onCreateView的时候 创建了 PagedListAdapter
     * 所以，如果arguments 有参数需要传递到Adapter 中，那么需要在getAdapter()方法中取出参数。
     *
     * @return adapter
     */
    public abstract PagedListAdapter getAdapter();
}