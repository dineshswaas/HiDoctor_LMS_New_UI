package com.swaas.kangle.LPCourse;

import android.content.Context;
import android.graphics.Color;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.swaas.kangle.utils.Constants;
import java.util.Arrays;
import java.util.Comparator;

public class SectionedGridRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int SECTION_TYPE = 0;
    /* access modifiers changed from: private */
    public RecyclerView.Adapter mBaseAdapter;
    private final Context mContext;
    private LayoutInflater mLayoutInflater;
    private RecyclerView mRecyclerView;
    private int mSectionResourceId;
    private SparseArray<Section> mSections = new SparseArray<>();
    private int mTextResourceId;
    /* access modifiers changed from: private */
    public boolean mValid = true;

    public SectionedGridRecyclerViewAdapter(Context context, int sectionResourceId, int textResourceId, RecyclerView recyclerView, RecyclerView.Adapter baseAdapter) {
        this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mSectionResourceId = sectionResourceId;
        this.mTextResourceId = textResourceId;
        this.mBaseAdapter = baseAdapter;
        this.mContext = context;
        this.mRecyclerView = recyclerView;
        this.mBaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            public void onChanged() {
                boolean unused = SectionedGridRecyclerViewAdapter.this.mValid = SectionedGridRecyclerViewAdapter.this.mBaseAdapter.getItemCount() > 0;
                SectionedGridRecyclerViewAdapter.this.notifyDataSetChanged();
            }

            public void onItemRangeChanged(int positionStart, int itemCount) {
                boolean unused = SectionedGridRecyclerViewAdapter.this.mValid = SectionedGridRecyclerViewAdapter.this.mBaseAdapter.getItemCount() > 0;
                SectionedGridRecyclerViewAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
            }

            public void onItemRangeInserted(int positionStart, int itemCount) {
                boolean unused = SectionedGridRecyclerViewAdapter.this.mValid = SectionedGridRecyclerViewAdapter.this.mBaseAdapter.getItemCount() > 0;
                SectionedGridRecyclerViewAdapter.this.notifyItemRangeInserted(positionStart, itemCount);
            }

            public void onItemRangeRemoved(int positionStart, int itemCount) {
                boolean unused = SectionedGridRecyclerViewAdapter.this.mValid = SectionedGridRecyclerViewAdapter.this.mBaseAdapter.getItemCount() > 0;
                SectionedGridRecyclerViewAdapter.this.notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
        final GridLayoutManager layoutManager = (GridLayoutManager) this.mRecyclerView.getLayoutManager();
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            public int getSpanSize(int position) {
                if (SectionedGridRecyclerViewAdapter.this.isSectionHeaderPosition(position)) {
                    return layoutManager.getSpanCount();
                }
                return 1;
            }
        });
    }

    public static class SectionViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public SectionViewHolder(View view, int mTextResourceid) {
            super(view);
            this.title = (TextView) view.findViewById(mTextResourceid);
            this.title.setBackgroundColor(Color.parseColor(Constants.COMPANY_COLOR));
            this.title.setTextColor(-16777216);
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int typeView) {
        if (typeView == 0) {
            return new SectionViewHolder(LayoutInflater.from(this.mContext).inflate(this.mSectionResourceId, parent, false), this.mTextResourceId);
        }
        return this.mBaseAdapter.onCreateViewHolder(parent, typeView - 1);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder sectionViewHolder, int position) {
        if (isSectionHeaderPosition(position)) {
            ((SectionViewHolder) sectionViewHolder).title.setText(this.mSections.get(position).title);
        } else {
            this.mBaseAdapter.onBindViewHolder(sectionViewHolder, sectionedPositionToPosition(position));
        }
    }

    public int getItemViewType(int position) {
        if (isSectionHeaderPosition(position)) {
            return 0;
        }
        return this.mBaseAdapter.getItemViewType(sectionedPositionToPosition(position)) + 1;
    }

    public static class Section {
        int firstPosition;
        int sectionedPosition;
        CharSequence title;

        public Section(int firstPosition2, CharSequence title2) {
            this.firstPosition = firstPosition2;
            this.title = title2;
        }

        public CharSequence getTitle() {
            return this.title;
        }
    }

    public void setSections(Section[] sections) {
        this.mSections.clear();
        Arrays.sort(sections, new Comparator<Section>() {
            public int compare(Section o, Section o1) {
                if (o.firstPosition == o1.firstPosition) {
                    return 0;
                }
                return o.firstPosition < o1.firstPosition ? -1 : 1;
            }
        });
        int offset = 0;
        for (Section section : sections) {
            section.sectionedPosition = section.firstPosition + offset;
            this.mSections.append(section.sectionedPosition, section);
            offset++;
        }
        notifyDataSetChanged();
    }

    public int positionToSectionedPosition(int position) {
        int offset = 0;
        int i = 0;
        while (i < this.mSections.size() && this.mSections.valueAt(i).firstPosition <= position) {
            offset++;
            i++;
        }
        return position + offset;
    }

    public int sectionedPositionToPosition(int sectionedPosition) {
        if (isSectionHeaderPosition(sectionedPosition)) {
            return -1;
        }
        int offset = 0;
        int i = 0;
        while (i < this.mSections.size() && this.mSections.valueAt(i).sectionedPosition <= sectionedPosition) {
            offset--;
            i++;
        }
        return sectionedPosition + offset;
    }

    public boolean isSectionHeaderPosition(int position) {
        return this.mSections.get(position) != null;
    }

    public long getItemId(int position) {
        if (isSectionHeaderPosition(position)) {
            return (long) (Integer.MAX_VALUE - this.mSections.indexOfKey(position));
        }
        return this.mBaseAdapter.getItemId(sectionedPositionToPosition(position));
    }

    public int getItemCount() {
        if (this.mValid) {
            return this.mBaseAdapter.getItemCount() + this.mSections.size();
        }
        return 0;
    }
}
