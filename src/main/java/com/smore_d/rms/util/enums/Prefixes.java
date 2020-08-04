package com.smore_d.rms.util.enums;

import java.util.ArrayList;
import java.util.List;

public enum Prefixes {

    HEAVY(5, -3.1f),
    LIGHT(2, -2.8f);


    public final int attackDamage;
    public final float attackSpeed;

    Prefixes(int attackDamage, float attackSpeed) {
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    public static List<Prefixes> PrefixList() {
        List<Prefixes> prefixes = new ArrayList<>();

        prefixes.add(HEAVY);
        prefixes.add(LIGHT);

        return prefixes;
    }

}
