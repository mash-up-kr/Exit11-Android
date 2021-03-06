package io.mashup.exit11.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.mashup.exit11.R;
import io.mashup.exit11.data.model.PartyDetail;
import io.mashup.exit11.ui.view.DetailPartyInfoView;
import io.mashup.exit11.util.LocationChoiceObserver;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class DetailPartyInfoFragment extends Fragment {

    public static DetailPartyInfoFragment newInstance() {
        return new DetailPartyInfoFragment();
    }

    @BindView(R.id.when_layout)
    LinearLayout whenLayout;

    @BindView(R.id.when_result_layout)
    DetailPartyInfoView whenResult;

    @BindView(R.id.where_layout)
    LinearLayout whereLayout;

    @BindView(R.id.where_result_layout)
    DetailPartyInfoView whereResult;

    @BindView(R.id.people_layout)
    LinearLayout peopleLayout;

    @BindView(R.id.people_result_layout)
    DetailPartyInfoView peopleResult;

    private StringBuilder timeStr;
    private String tempStr;

    private String address;

    private int peopleNumber;

    private PublishSubject<Boolean> locationChoice = PublishSubject.create();
    private PublishSubject<PartyDetail> partyDetail = PublishSubject.create();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_party_info, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LocationChoiceObserver.getInstance().getObservable().subscribe(location -> {
            address = location;

            locationChoice.onNext(false);

            whereLayout.setVisibility(View.INVISIBLE);
            whereResult.setVisibility(View.VISIBLE);
            whereResult.setActiveImg(R.drawable.enrollment_icon_map_active);
            whereResult.setQuestion("어디서");
            whereResult.setResult(location);
        });
    }

    @OnClick({R.id.when_layout, R.id.when_result_layout, R.id.where_layout, R.id.where_result_layout,
              R.id.people_layout, R.id.people_result_layout})
    public void detailInfoClicked(View v) {
        switch (v.getId()) {
            case R.id.when_layout:
            case R.id.when_result_layout:
                setTimeDialog();
                break;
            case R.id.where_layout:
            case R.id.where_result_layout:
                setLocationOnMap();
                break;
            case R.id.people_layout:
            case R.id.people_result_layout:
                setPeopleNumber();
                break;
        }
    }


    private void setPeopleNumber() {
        PeopleMeetFragement peopleMeetFragement = PeopleMeetFragement.newInstance();
        peopleMeetFragement.setOnAdapterItemClickListener(new PeopleMeetFragement.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(int num) {
                peopleNumber = num;

                //결과 표시
                peopleLayout.setVisibility(View.INVISIBLE);
                peopleResult.setVisibility(View.VISIBLE);
                peopleResult.setActiveImg(R.drawable.enrollment_icon_people_active);
                peopleResult.setQuestion("몇명과");
                peopleResult.setResult(peopleNumber + "명");

                partyDetail.onNext(new PartyDetail(timeStr.toString(), address, num));
            }
        });

        getChildFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.frame_container, peopleMeetFragement)
                .commit();
    }


    /**
     * bottom sheet collapsed
     * So, daum map opened
     */
    private void setLocationOnMap() {
        locationChoice.onNext(true);
    }

    private void setTimeDialog() {
        DatePickerFragment dateDialog = new DatePickerFragment();
        dateDialog.show(getFragmentManager(), "datePicker");
        dateDialog.setOnAdapterItemClickListener(new DatePickerFragment.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(String day, String dayOfWeek) {
                timeStr = new StringBuilder();
                timeStr.append(day);
                tempStr = dayOfWeek;

                TimePickerFragment timeDialog = new TimePickerFragment();
                timeDialog.show(getFragmentManager(), "timePicker");
                timeDialog.setOnAdapterItemClickListener(new TimePickerFragment.OnAdapterItemClickLIstener() {
                    @Override
                    public void onAdapterItemClick(String time) {
                        timeStr.append(time);
                        timeStr.append(tempStr);

                        //결과 표시
                        whenLayout.setVisibility(View.INVISIBLE);
                        whenResult.setVisibility(View.VISIBLE);
                        whenResult.setActiveImg(R.drawable.enrollment_icon_clock_active);
                        whenResult.setQuestion("언제");
                        whenResult.setResult(timeStr.toString());
                    }
                });
            }
        });
    }

    public Observable<Boolean> getLocationChoiceSubject() {
        return locationChoice;
    }

    public Observable<PartyDetail> getDetailPartySubject() {
        return partyDetail;
    }
}
