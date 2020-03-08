
public class Example extends AppCompatActivity {

    .....

    private void callApi() {
        NetRequestManager.OnResponse onResponse = new NetRequestManager.OnResponse() {
            @Override
            public void onSuccess(String response) {
                ArrayList<Category> data = new Gson().fromJson(response, new TypeToken<List<Category>>() {}.getType());
                adapter.setData(data);
            }

            @Override
            public void onError(VolleyError error) {
                int statusCode = error.networkResponse.statusCode;
                String body = new String(error.networkResponse.data,"UTF-8");
                .....
                finish();
            }
        };
        HashMap<String, String> params = new HashMap<>();
        params.put("token", FirePreference.getToken(this));

        new NetRequestManager.Builder()
                .setEndPoint(NetRequestManager.HOST + "/category")
                .setHeader(params)
                .setOnResponse(onResponse)
                .build()
                .get();

    }

    .....

}
