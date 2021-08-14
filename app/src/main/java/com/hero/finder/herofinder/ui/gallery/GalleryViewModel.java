package com.hero.finder.herofinder.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.PaymentError;
import com.PaymentSuccess;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private final MutableLiveData<Boolean> onSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> onError = new MutableLiveData<>();

    private PaymentError paymentError;
    private PaymentSuccess paymentSuccess;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Input Display");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Boolean> getOnSuccess() {
        return onSuccess;
    }

    public LiveData<Boolean> getOnError() {
        return onError;
    }

    public void setOnSuccess(Boolean b) {
        onSuccess.setValue(b);
    }

    public void setOnError(Boolean b) {
        onError.setValue(b);
    }

    public PaymentError getPaymentError() {
        return paymentError;
    }

    public void setPaymentError(PaymentError paymentError) {
        this.paymentError = paymentError;
    }

    public PaymentSuccess getPaymentSuccess() {
        return paymentSuccess;
    }

    public void setPaymentSuccess(PaymentSuccess paymentSuccess) {
        this.paymentSuccess = paymentSuccess;
    }
}