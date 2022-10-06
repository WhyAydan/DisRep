package repbot.statistic;

import jdautil.localization.util.Replacement;

import java.util.List;

@FunctionalInterface
public interface ReplacementProvider {
    List<Replacement> replacements();
}
