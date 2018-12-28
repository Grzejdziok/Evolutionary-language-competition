package cli;

import org.apache.commons.cli.Options;

/**
 * An interface for factory classes serving to create {@code Options} objects
 */
public interface OptionsCreator {

    /**
     * Returns an {@code Options} object
     * @return an {@code Options} object
     */
    Options create();

}
