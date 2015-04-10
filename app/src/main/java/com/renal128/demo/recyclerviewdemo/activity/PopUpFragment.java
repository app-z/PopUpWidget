package com.renal128.demo.recyclerviewdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renal128.demo.recyclerviewdemo.R;
import com.renal128.demo.recyclerviewdemo.adapter.RecyclerViewAdapter;
import com.renal128.demo.recyclerviewdemo.model.Record;

import java.util.ArrayList;
import java.util.List;

public class PopUpFragment extends Fragment{

    private static final String ARG_TIMER_INTERVAL = "timer_interval";
    private OnFragmentInteractionListener mListener;
    private HandlerOverlayMessages messageHandler;

    private int TIMER_INTERVAL_DEFAULT = 3000;

    private int timer_interval;

    private RecyclerViewAdapter adapter;
    private CardView cardView;
    private RecyclerView recyclerView;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param timer_interval .
     * @return A new instance of fragment PlusOneFragment.
     */
    public static PopUpFragment newInstance(int timer_interval) {
        PopUpFragment fragment = new PopUpFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TIMER_INTERVAL, timer_interval);
        fragment.setArguments(args);
        return fragment;
    }

    public static PopUpFragment newInstance() {
        PopUpFragment fragment = new PopUpFragment();
        return fragment;
    }

    public PopUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            timer_interval = getArguments().getInt(ARG_TIMER_INTERVAL);
        } else {
            timer_interval = TIMER_INTERVAL_DEFAULT;
        }

        // keep the fragment and all its data across screen rotation
        //setRetainInstance(true);

        messageHandler = new HandlerOverlayMessages(this);
    }


    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            removeMessagePopUp();
            timerHandler.postDelayed(this, timer_interval);
        }
    };


    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        timerHandler.postDelayed(timerRunnable, timer_interval);
    }

    @Override
    public void onStart() {
        super.onStart();
        mListener.onPopUpFragmentStart();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popup, container, false);

        List<Record> records = new ArrayList<Record>();

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(records);
        LinearLayoutManager layoutManager = new MyLinearLayoutManager(getActivity());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);

        cardView = (CardView)view.findViewById(R.id.cardView);
        return view;
    }


    public void addMessage0ToPopUp(int type, String text){
        Bundle msgBundle = new Bundle();
        msgBundle.putInt(HandlerOverlayMessages.ICON_ARG, type);
        msgBundle.putString(HandlerOverlayMessages.TEXT_ARG, text);
        Message msg = new Message();
        msg.what = HandlerOverlayMessages.ADD_MESSAGE;
        msg.setData(msgBundle);
        messageHandler.sendMessage(msg);
    }

    public void removeMessagePopUp() {
        Bundle msgBundle = new Bundle();
        Message msg = new Message();
        msg.what = HandlerOverlayMessages.REMOVE_MESSAGE_0;
        msg.setData(msgBundle);
        messageHandler.sendMessage(msg);
    }


    private void addMessageInternal(int type, String text) {
        Record record = new Record();
        record.setName(text);
        record.setType(Record.Type.values()[type]);

        adapter.getRecords().add(record);
        adapter.notifyDataSetChanged();

        if ( adapter.getItemCount() > 0 ) {
            cardView.setVisibility(View.VISIBLE);
            mListener.onShowPopUpFrugment();
        }
    }


    private void removeMessage0Internal(){
        if ( adapter.getItemCount() > 0 ) {
            adapter.getRecords().remove(0);
            adapter.notifyDataSetChanged();
        }
        if ( adapter.getItemCount() == 0 ) {
            cardView.setVisibility(View.GONE);
            mListener.onHidePopUpFrugment();
        }
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onPopUpFragmentStart();
        void onHidePopUpFrugment();
        void onShowPopUpFrugment();
    }


    public class MyLinearLayoutManager extends LinearLayoutManager {

        public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout)    {
            super(context, orientation, reverseLayout);
        }

        public MyLinearLayoutManager(Context context)    {
            super(context);
        }

        private int[] mMeasuredDimension = new int[2];

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
                              int widthSpec, int heightSpec) {
            final int widthMode = View.MeasureSpec.getMode(widthSpec);
            final int heightMode = View.MeasureSpec.getMode(heightSpec);
            final int widthSize = View.MeasureSpec.getSize(widthSpec);
            final int heightSize = View.MeasureSpec.getSize(heightSpec);
            int width = 0;
            int height = 0;
            for (int i = 0; i < getItemCount(); i++) {
                measureScrapChild(recycler, i,
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        mMeasuredDimension);

                if (getOrientation() == HORIZONTAL) {
                    width = width + mMeasuredDimension[0];
                    if (i == 0) {
                        height = mMeasuredDimension[1];
                    }
                } else {
                    height = height + mMeasuredDimension[1];
                    if (i == 0) {
                        width = mMeasuredDimension[0];
                    }
                }
            }
            switch (widthMode) {
                case View.MeasureSpec.EXACTLY:
                    width = widthSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            switch (heightMode) {
                case View.MeasureSpec.EXACTLY:
                    height = heightSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            setMeasuredDimension(width, height);
        }

        private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
                                       int heightSpec, int[] measuredDimension) {
            View view = recycler.getViewForPosition(position);
            if (view != null) {
                RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
                int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                        getPaddingLeft() + getPaddingRight(), p.width);
                int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                        getPaddingTop() + getPaddingBottom(), p.height);
                view.measure(childWidthSpec, childHeightSpec);
                measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                recycler.recycleView(view);
            }
        }
    }

    private class HandlerOverlayMessages <T> extends Handler {

        public static final int ADD_MESSAGE = 100;
        public static final int REMOVE_MESSAGE_0 = 101;
        public static final String TEXT_ARG = "text";
        public static final String ICON_ARG = "icon";

        private final T fragment;

        public HandlerOverlayMessages(T fragment ){
            this.fragment = fragment;
        }

        @Override
        public void handleMessage(Message message){
            if (this.fragment != null){

                Bundle b = message.getData();

                switch (message.what){
                    case ADD_MESSAGE:
                        if(b == null)
                            new IllegalArgumentException("Message should be have params !");

                        String text = b.getString(TEXT_ARG);
                        int type = b.getInt(ICON_ARG);
                        ((PopUpFragment)fragment).addMessageInternal(type, text);
                        break;
                    case REMOVE_MESSAGE_0:
                        ((PopUpFragment)fragment).removeMessage0Internal();
                        break;
                }
            }
        }

    }


}
