package com.example.convidados.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.convidados.constants.GuestConstants;
import com.example.convidados.model.FeedbackModel;
import com.example.convidados.model.GuestModel;
import com.example.convidados.repository.GuestRepository;

import java.util.List;

public class AllGuestViewModel extends AndroidViewModel {

    private GuestRepository mRepository;

    private MutableLiveData<List<GuestModel>> mGuestList = new MutableLiveData<>();
    public LiveData<List<GuestModel>> guestList = this.mGuestList;

    private MutableLiveData<FeedbackModel> mFeedback = new MutableLiveData<FeedbackModel>();
    public LiveData<FeedbackModel> feedback = this.mFeedback;

    public AllGuestViewModel(@NonNull Application application) {
        super(application);
        this.mRepository = GuestRepository.getInstance(application.getApplicationContext());
    }

        public void getList(int filter) {
            if (filter == GuestConstants.CONFIRMATION.NOT_CONFIRMED) {
                this.mGuestList.setValue(this.mRepository.getAll());
            } else if (filter == GuestConstants.CONFIRMATION.PRESENT) {
                this.mGuestList.setValue(this.mRepository.getPresents());
            } else if (filter == GuestConstants.CONFIRMATION.ABSENT) {
                this.mGuestList.setValue(this.mRepository.getAbsents());
            }

        }

    public void delete(int id) {
        if (this.mRepository.delete(id)) {
            this.mFeedback.setValue(new FeedbackModel("Convidado removido com sucesso"));
        } else {
            this.mFeedback.setValue(new FeedbackModel("Erro inesperado", false));
        }
    }
}