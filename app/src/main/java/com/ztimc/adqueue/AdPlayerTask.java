package com.ztimc.adqueue;

public class AdPlayerTask implements Task<AdContent> {

    public Advert mAdvert;

    public void setAdvert(Advert advert) {
        mAdvert = advert;
    }

    @Override
    public AdContent execute() {
        return new AdContent();
    }
}
