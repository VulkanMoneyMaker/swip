package tut.mawrqns.jol.network;



import retrofit2.Call;
import retrofit2.http.GET;
import tut.mawrqns.jol.network.model.Model;

public interface ApiCasino {

    @GET("vul-57")
    Call<Model> check();

    @GET("vul-57-casino")
    Call<Model> checkUnableCasino();

}
