package repbot.dao.pagination;

import repbot.dao.snapshots.RepProfile;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class GuildRanking extends PageAccess<RepProfile> {
    private final String title;

    public GuildRanking(String title, Supplier<Integer> pagecount, Function<Integer, List<RepProfile>> pageSupplier) {
        super(pagecount, pageSupplier);
        this.title = title;
    }

    public String title() {
        return title;
    }
}
