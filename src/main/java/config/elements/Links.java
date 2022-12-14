package repbot.config.elements;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal", "CanBeFinal"})
public class Links {
    private String tos = "";
    private String invite = "https://discord.com/oauth2/authorize?client_id=834843896579489794&scope=bot&permissions=1342532672";
    private String support = "";
    private String website = "https://rainbowdashlabs.github.io/reputation-bot/";
    private String faq = "https://rainbowdashlabs.github.io/reputation-bot/faq";

    public String tos() {
        return tos;
    }

    public String invite() {
        return invite;
    }

    public String support() {
        return support;
    }

    public String website() {
        return website;
    }

    public String faq() {
        return faq;
    }
}
