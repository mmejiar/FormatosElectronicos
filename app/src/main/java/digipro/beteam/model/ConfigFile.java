package digipro.beteam.model;

public class ConfigFile {
    private String url;
    private String user;
    private String password;
    private String wsusuario;
    private String wsimagenes;
    private String wcffiletransfer;

    public String getWsusuario() { return wsusuario; }
    public void setWsusuario(String wsusuario) { this.wsusuario = wsusuario; }
    public String getWsimagenes() { return wsimagenes;  }
    public void setWsimagenes(String wsimagenes) {this.wsimagenes = wsimagenes;}
    public String getWcffiletransfer() { return wcffiletransfer; }
    public void setWcffiletransfer(String wcffiletransfer) { this.wcffiletransfer = wcffiletransfer; }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) { this.url = url; }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
