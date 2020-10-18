package no.realitylab.arface.internetService;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("api/categories") //"posts" is a relative url
    Call<TopLevel> getTopLevel();
    Call<CategoryDetails> getCategoryDetails();

}

