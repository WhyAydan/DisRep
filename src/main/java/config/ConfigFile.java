package repbot.config;

import repbot.config.elements.AnalyzerSettings;
import repbot.config.elements.Api;
import repbot.config.elements.Badges;
import repbot.config.elements.BaseSettings;
import repbot.config.elements.Botlist;
import repbot.config.elements.Database;
import repbot.config.elements.Links;
import repbot.config.elements.MagicImage;
import repbot.config.elements.PresenceSettings;
import repbot.config.elements.SelfCleanup;

@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
public class ConfigFile {
    private BaseSettings baseSettings = new BaseSettings();
    private PresenceSettings presenceSettings = new PresenceSettings();
    private AnalyzerSettings analyzerSettings = new AnalyzerSettings();
    private Database database = new Database();
    private MagicImage magicImage = new MagicImage();
    private Badges badges = new Badges();
    private Links links = new Links();
    private Botlist botlist = new Botlist();
    private Api api = new Api();
    private SelfCleanup selfcleanup = new SelfCleanup();

    public BaseSettings baseSettings() {
        return baseSettings;
    }

    public PresenceSettings presence() {
        return presenceSettings;
    }

    public AnalyzerSettings analyzerSettings() {
        return analyzerSettings;
    }

    public Database database() {
        return database;
    }

    public MagicImage magicImage() {
        return magicImage;
    }

    public Badges badges() {
        return badges;
    }

    public Links links() {
        return links;
    }

    public Botlist botlist() {
        return botlist;
    }

    public SelfCleanup selfCleanup() {
        return selfcleanup;
    }

    public Api api() {
        return api;
    }
}
