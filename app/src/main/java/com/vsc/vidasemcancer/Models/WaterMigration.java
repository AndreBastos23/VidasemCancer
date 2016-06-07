package com.vsc.vidasemcancer.Models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by Eduardo on 04/06/2016.
 */
public class WaterMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema realmSchema = realm.getSchema();

        if (oldVersion == 0) {
            RealmObjectSchema waterSchema = realmSchema.get("Water");
            waterSchema.addField("dDate", Date.class).transform(new RealmObjectSchema.Function() {

                                                                    @Override
                                                                    public void apply(DynamicRealmObject obj) {
                                                                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                                                        try {
                                                                            obj.set("dDate", format.parse(obj.getString("date")));
                                                                        } catch (ParseException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                    }
                                                                }


            );
            oldVersion++;

        }

        realm.close();
    }

}
