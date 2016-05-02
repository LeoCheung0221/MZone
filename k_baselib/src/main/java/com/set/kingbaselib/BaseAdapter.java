package com.set.kingbaselib;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.set.kingbaselib.animation.AlphaInAnimation;
import com.set.kingbaselib.animation.BaseAnimation;
import com.set.kingbaselib.animation.ScaleInAnimation;
import com.set.kingbaselib.animation.SlideInBottomAnimation;
import com.set.kingbaselib.animation.SlideInLeftAnimation;
import com.set.kingbaselib.animation.SlideInRightAnimation;
import com.set.kingbaselib.listener.OnRvItemChildClickListener;
import com.set.kingbaselib.listener.OnRvItemClickListener;
import com.set.kingbaselib.listener.RequestLoadMoreListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者： LeoCheung4ever on 2016/5/1 08:35
 * 邮箱： leocheung4ever@gmail.com
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @IntDef({ALPHA_IN, SCALE_IN, SLIDE_IN_LEFT, SLIDE_IN_RIGHT, SLIDE_IN_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    /**
     * Use with {@link #openLoadAnimaion}
     */
    public static final int ALPHA_IN = 0x00000001;
    public static final int SCALE_IN = 0x00000002;
    public static final int SLIDE_IN_LEFT = 0x00000003;
    public static final int SLIDE_IN_RIGHT = 0x00000004;
    public static final int SLIDE_IN_BOTTOM = 0x00000005;

    protected static final String TAG = BaseAdapter.class.getSimpleName();

    protected static final int HEADER_VIEW = 0x00000011;
    protected static final int LOADING_VIEW = 0x00000012;
    protected static final int FOOTER_VIEW = 0x00000013;
    protected static final int EMPTY_VIEW = 0x00000014;
    protected List<T> mData;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected int mLayoutResId;

    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 300;
    private int mLastPosition = 1;

    @AnimationType
    private BaseAnimation mCustomAnimation;
    private BaseAnimation mSetAnimation = new AlphaInAnimation(); //默认动画
    private boolean mNextLoadEnable = false; //是否可以下次加载
    private boolean mLoadingMoreEnable = false; //是否可以加载更多
    private boolean mFirstOnlyEnable = true; //是否仅有第一次加载进的列表项可以有动画-->只要滑动展示过的item就不设置动画,尤其是往上滑动
    private boolean mOpenAnimationEnable = false; //是否可以打开动画
    private boolean mEmptyEnable; //是否可以显示EmptyView

    private View mHeaderView;
    private View mFooterView;
    private View mEmptyView;

    private OnRvItemClickListener onRvItemClickListener;
    private RequestLoadMoreListener mRequestLoadMoreListener;
    private OnRvItemChildClickListener mChildClickListener;

    /* ~~~~~~~~~~~~~~~~~~~ Constructor ~~~~~~~~~~~~~~~~~~~~ */
    public BaseAdapter(Context context, int layoutId, List<T> data) {
        this.mLayoutResId = layoutId;
        this.mContext = context;
        this.mData = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
    }

    public BaseAdapter(Context context, List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.mContext = context;
    }

    /* ~~~~~~~~~~~~~~~~~~~~ 覆写方法  ~~~~~~~~~~~~~~~~~~~~~~ */

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return onCreateDefViewHolder(parent, viewType);
        } else if (viewType == LOADING_VIEW) {
            return new FooterViewHolder(getItemView(R.layout.def_loading, parent));
        } else if (viewType == HEADER_VIEW) {
            return new HeaderViewHolder(mHeaderView);
        } else if (viewType == FOOTER_VIEW) {
            return new FooterViewHolder(mFooterView);
        } else if (viewType == EMPTY_VIEW) {
            return new EmptyViewHolder(mEmptyView);
        } else {
            return onCreateDefViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ContentViewHolder) {
            int index = position - getHeaderViewsCount();
            BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
            convert(baseViewHolder, mData.get(index));
            if (onRvItemClickListener != null) {
                baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRvItemClickListener.onItemClick(v, position - getHeaderViewsCount());
                    }
                });
            }
            if (mOpenAnimationEnable) {
                if (!mFirstOnlyEnable || position > mLastPosition) {
                    BaseAnimation animation = null;
                    if (mCustomAnimation != null) {
                        animation = mCustomAnimation;
                    } else {
                        animation = mSetAnimation;
                    }
                    for (Animator anim : animation.getAnimators(holder.itemView)) {
                        anim.setDuration(mDuration).start();
                        anim.setInterpolator(mInterpolator);
                    }
                    mLastPosition = position;
                }
            }
        } else if (holder instanceof FooterViewHolder) {
            if (mNextLoadEnable && !mLoadingMoreEnable && mRequestLoadMoreListener != null) {
                mLoadingMoreEnable = true;
                mRequestLoadMoreListener.onLoadMore();
                if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                    params.setFullSpan(true);
                }
            }
        } else if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof EmptyViewHolder) {

        } else {
            int index = position - getHeaderViewsCount();
            BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
            onBindDefViewHolder(baseViewHolder, mData.get(index));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return HEADER_VIEW;
        } else if (mEmptyView != null && getItemCount() == 1) {
            return EMPTY_VIEW;
        } else if (position == mData.size() + getHeaderViewsCount()) {
            if (mNextLoadEnable) {
                return LOADING_VIEW;
            } else {
                return FOOTER_VIEW;
            }
        }
        return getDefItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int i = mNextLoadEnable ? 1 : 0; //如果可以下次加载,则多出一个View在底部
        int count = mData.size() + i + getHeaderViewsCount() + getFooterViewsCount();
        mEmptyEnable = false;
        if (count == 0) {
            mEmptyEnable = true;
            count += getEmptyViewCount();
        }
        return count;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(getItemView(mLayoutResId, parent));
    }

    protected View getItemView(int layoutResId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
    }

    /* ~~~~~~~~~~~~~~~~~~~~ 对外抽象方法  ~~~~~~~~~~~~~~~~~~~~~~ */
    abstract protected void convert(BaseViewHolder helper, T item);

    /* ~~~~~~~~~~~~~~~~~~~~ 对外公开方法 (包括对子类) ~~~~~~~~~~~~~~~~~~~~~~ */
    public int getHeaderViewsCount() {
        return mHeaderView == null ? 0 : 1;
    }

    public int getFooterViewsCount() {
        return mFooterView == null ? 0 : 1;
    }

    public int getEmptyViewCount() {
        return mEmptyView == null ? 0 : 1;
    }

    public void addHeaderView(View header) {
        if (header == null)
            throw new RuntimeException("header is null");
        this.mHeaderView = header;
        this.notifyDataSetChanged();
    }

    public void addFooterView(View footer) {
        mNextLoadEnable = false;
        if (footer == null)
            throw new RuntimeException("footer is null");
        this.mFooterView = footer;
        this.notifyDataSetChanged();
    }

    /**
     * set the view show to show if the adapter's data is empty
     */
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    /**
     * When the current adapter is empty, the BaseAdapter can display a special view
     * called the empty view. The empty view is used to provide feedback to the user
     * that no data is available in this AdapterView.
     *
     * @return The view to show if the adapter is empty.
     */
    public View getEmptyView() {
        return mEmptyView;
    }

    public void isNextLoad(boolean isNextLoad) {
        mNextLoadEnable = isNextLoad;
        mLoadingMoreEnable = false;
        notifyDataSetChanged();
    }

    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * two item type can override it
     *
     * @param holder
     * @param item
     */
    protected void onBindDefViewHolder(BaseViewHolder holder, T item) {

    }

    public void setOnRvItemClickListener(OnRvItemClickListener itemClickListener) {
        this.onRvItemClickListener = itemClickListener;
    }

    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener) {
        mNextLoadEnable = true;
        this.mRequestLoadMoreListener = requestLoadMoreListener;
    }

    public void setOnRvItemChildClickListener(OnRvItemChildClickListener childClickListener) {
        this.mChildClickListener = childClickListener;
    }

    /**
     * set the view animation type
     *
     * @param animationType One of {@link #ALPHA_IN},{@link #SCALE_IN},{@link #SLIDE_IN_LEFT},{@link #SLIDE_IN_RIGHT},{@link #SLIDE_IN_BOTTOM}
     */
    public void openLoadAnimaion(@AnimationType int animationType) {
        this.mOpenAnimationEnable = true;
        mCustomAnimation = null;
        switch (animationType) {
            case ALPHA_IN:
                mSetAnimation = new AlphaInAnimation();
                break;
            case SCALE_IN:
                mSetAnimation = new ScaleInAnimation();
                break;
            case SLIDE_IN_BOTTOM:
                mSetAnimation = new SlideInBottomAnimation();
                break;
            case SLIDE_IN_LEFT:
                mSetAnimation = new SlideInLeftAnimation();
                break;
            case SLIDE_IN_RIGHT:
                mSetAnimation = new SlideInRightAnimation();
                break;
        }
    }

    /**
     * Set Custom ObjectAnimator
     *
     * @param animation ObjectAnimator
     */
    public void openLoadAnimation(BaseAnimation animation) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = animation;
    }

    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }

    public void isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void add(int position, T item) {
        mData.add(position, item);
        notifyItemInserted(position);
    }

    public List getData() {
        return mData;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    public T getItem(int position) {
        return mData.get(position);
    }

    /* ~~~~~~~~~~~~~~~~~~~~~ inner class ~~~~~~~~~~~~~~~~~~~~~~~*/
    public static class ContentViewHolder extends BaseViewHolder {

        public ContentViewHolder(View itemView) {
            super(itemView.getContext(), itemView);
        }
    }

    public static class FooterViewHolder extends BaseViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView.getContext(), itemView);
        }
    }

    public static class HeaderViewHolder extends BaseViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView.getContext(), itemView);
        }
    }

    public static class EmptyViewHolder extends BaseViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView.getContext(), itemView);
        }
    }

    public class OnItemChildClickListener implements View.OnClickListener {

        public int position;

        @Override
        public void onClick(View v) {
            if (mChildClickListener != null) {
                mChildClickListener.onItemChildClick(BaseAdapter.this, v, position - getHeaderViewsCount());
            }
        }
    }
}
