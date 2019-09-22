package habib.angpdjms.nel.network;


import habib.angpdjms.nel.network.model.ModelRequest;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("vul-58")
    Call<ModelRequest> check();

}
