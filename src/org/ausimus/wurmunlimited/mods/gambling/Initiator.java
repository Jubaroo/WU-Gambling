package org.ausimus.wurmunlimited.mods.gambling;
/*
     ___          ___          ___                     ___          ___          ___
    /\  \        /\__\        /\  \         ___       /\__\        /\__\        /\  \
   /::\  \      /:/  /       /::\  \       /\  \     /::|  |      /:/  /       /::\  \
  /:/\:\  \    /:/  /       /:/\ \  \      \:\  \   /:|:|  |     /:/  /       /:/\ \  \
 /::\~\:\  \  /:/  /  ___  _\:\~\ \  \     /::\__\ /:/|:|__|__  /:/  /  ___  _\:\~\ \  \
/:/\:\ \:\__\/:/__/  /\__\/\ \:\ \ \__\ __/:/\/__//:/ |::::\__\/:/__/  /\__\/\ \:\ \ \__\
\/__\:\/:/  /\:\  \ /:/  /\:\ \:\ \/__//\/:/  /   \/__/~~/:/  /\:\  \ /:/  /\:\ \:\ \/__/
     \::/  /  \:\  /:/  /  \:\ \:\__\  \::/__/          /:/  /  \:\  /:/  /  \:\ \:\__\
     /:/  /    \:\/:/  /    \:\/:/  /   \:\__\         /:/  /    \:\/:/  /    \:\/:/  /
    /:/  /      \::/  /      \::/  /     \/__/        /:/  /      \::/  /      \::/  /
    \/__/        \/__/        \/__/                   \/__/        \/__/        \/__/

*/
import org.ausimus.wurmunlimited.mods.gambling.actions.ActionsInit;
import org.ausimus.wurmunlimited.mods.gambling.items.GItemTemplateCreator;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Initiator implements WurmServerMod, PreInitable, ServerStartedListener, ItemTemplatesCreatedListener {

    @Override
    public void preInit() {
        ModActions.init();
        WriteLog("ModActions Initiated.");
    }

    @Override
    public void onServerStarted() {
        new ActionsInit();
        WriteLog("Actions Registered.");
    }

    @Override
    public void onItemTemplatesCreated() {
        new GItemTemplateCreator();
        WriteLog("Templates Created.");
    }

    /**
     * @param data Whats logged
     */
    public static void WriteLog(String data) {
        try {
            String logFile = "mods/GambleMod/log.txt";
            FileWriter writeLog = new FileWriter(logFile, true);
            BufferedWriter bufferedLogWriter = new BufferedWriter(writeLog);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date timeStamp = new Date();
            bufferedLogWriter.write("==========================================================\n");
            bufferedLogWriter.write(dateFormat.format(timeStamp) + "\n");
            bufferedLogWriter.write(data + "\n");
            bufferedLogWriter.write("==========================================================\n");
            bufferedLogWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            Initiator.WriteLog(String.valueOf(ex));
        }
    }
}