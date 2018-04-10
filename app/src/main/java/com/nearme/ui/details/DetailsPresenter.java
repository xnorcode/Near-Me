package com.nearme.ui.details;

import com.nearme.data.source.PlacesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xnorcode on 10/04/2018.
 */

public class DetailsPresenter implements DetailsContract.Presenter {


    // Details Activity View
    private DetailsContract.View mView;


    // Places repository for local and remote transactions
    private PlacesRepository mPlacesRepository;


    // RxJava disposable manager for all Rx operations
    private CompositeDisposable mCompositeDisposable;


    /**
     * Constructor
     *
     * @param mPlacesRepository    for local and remote transactions
     * @param mCompositeDisposable for managing all Rx operations
     */
    public DetailsPresenter(PlacesRepository mPlacesRepository, CompositeDisposable mCompositeDisposable) {
        this.mPlacesRepository = mPlacesRepository;
        this.mCompositeDisposable = mCompositeDisposable;
    }


    /**
     * Register a view
     *
     * @param view of presenter
     */
    @Override
    public void registerView(DetailsContract.View view) {
        mView = view;
    }


    /**
     * Release all memory references
     */
    @Override
    public void dropView() {
        mView = null;
        mCompositeDisposable.clear();
        mPlacesRepository = null;
    }


    /**
     * Load place from local cache
     *
     * @param id of place
     */
    @Override
    public void getPlace(String id) {
        try {
            Disposable disposable = mPlacesRepository.getPlace(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(place -> mView.showPlace(place));
            mCompositeDisposable.add(disposable);
        } catch (Exception e) {
            e.printStackTrace();
            mView.showError(e.getMessage());
        }
    }
}
