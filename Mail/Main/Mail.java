package Mail.Main;

public class Mail {

    private String IPmandante;
    private String emailMandante;
    private String emailDestinatario;
    private String contenutoMail;
    private String oggettoMail;
    private boolean deleteFlagActive, letta;

    public Mail() {
        deleteFlagActive = false;
        letta = false;
    }

    public Mail(String emailMandante, String emailDestinatario, String oggettoMail, String contenutoMail) {
        this.emailMandante = emailMandante;
        this.oggettoMail = oggettoMail;
        this.emailDestinatario = emailDestinatario;
        this.contenutoMail = contenutoMail;
    }

    public void setIpmandante(String Ipmandante) {
        String[] IP = Ipmandante.split(":");
        this.IPmandante = IP[1];
    }

    public void setEmailMandante(String emailMandante) {
        String[] eMand = emailMandante.split(":");
        this.emailMandante = eMand[1];
    }

    public void setEmailDestinatario(String emailDestinatario) {
        String[] eDest = emailDestinatario.split(":");
        this.emailDestinatario = eDest[1];
    }

    public void setContenutoMail(String contenutoMail) {
        System.out.println(contenutoMail);
        String[] eCont = contenutoMail.split(":");
        this.oggettoMail = eCont[0];
        this.contenutoMail = eCont[1];
    }

    public void mailLetta() {
        letta = true;
    }

    public void setDeleteFlag() {
        this.deleteFlagActive = true;
    }

    public String getIpmandante() {
        return IPmandante;
    }

    public String getEmailMandante() {
        return emailMandante;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public String getContenutoMail() {
        return contenutoMail;
    }

    public String getOggettoMail() {
        return oggettoMail;
    }

    public boolean getDeleteFlag() {
        return deleteFlagActive;
    }

    public boolean getLetta() {
        return letta;
    }

    public String toString() {
        return "Ip mandante: " + IPmandante + "\nEmail mandante: " + emailMandante + 
                "\nEmail destinatario: " + emailDestinatario + "\nOggetto: " + oggettoMail +
                "\nContenuto:  " + contenutoMail;
    }
}