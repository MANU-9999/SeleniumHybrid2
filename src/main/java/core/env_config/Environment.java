package core.env_config;

public class Environment {

    private String url;
    private int max_attempts;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getMax_attempts() {
        return max_attempts;
    }
    public void setMax_attempts(int max_attempts) {
        this.max_attempts = max_attempts;
    }

}
