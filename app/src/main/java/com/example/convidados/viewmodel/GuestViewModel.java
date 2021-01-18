package com.example.convidados.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.convidados.model.FeedbackModel;
import com.example.convidados.model.GuestModel;
import com.example.convidados.repository.GuestRepository;

public class GuestViewModel extends AndroidViewModel {

    private GuestRepository mRepository;

    private MutableLiveData<GuestModel> mGuest = new MutableLiveData<>();
    public LiveData<GuestModel> guest = this.mGuest;

    private MutableLiveData<FeedbackModel> mFeedback = new MutableLiveData<>();
    public LiveData<FeedbackModel> feedback = this.mFeedback;

    public GuestViewModel(@NonNull Application application) {
        super(application);
        this.mRepository = GuestRepository.getInstance(application.getApplicationContext());
    }

    public void save(GuestModel guest) {

        if ("".equals(guest.getName())) {
            this.mFeedback.setValue(new FeedbackModel("Nome obrigatório!", false));
            return;
        }

        if (guest.getId() == 0) {
            if (this.mRepository.insert(guest)){
                this.mFeedback.setValue(new FeedbackModel("Convidado inserido com sucesso"));
            } else {
                this.mFeedback.setValue(new FeedbackModel("Erro inesperado", false));
            }

        } else {
            if (this.mRepository.update(guest)){
                this.mFeedback.setValue(new FeedbackModel("Convidado atualizado com sucesso"));
            } else {
                this.mFeedback.setValue(new FeedbackModel("Erro inesperado", false));
            }
        }
    }

    public void load(int id) {
        this.mGuest.setValue(this.mRepository.load(id));
    }

}
