package classes.arenas;

import abstracts.Arena;
import abstracts.Enemy;

public class Cave extends Arena {
    public Cave(Enemy enemy) {
        super("Mağara", enemy);
    }
}
