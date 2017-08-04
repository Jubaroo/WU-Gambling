package org.ausimus.wurmunlimited.mods.gambling.actions;
import com.wurmonline.server.*;
import com.wurmonline.server.economy.MonetaryConstants;
import com.wurmonline.server.items.*;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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

public class Roll implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer {
    private static short actionID;
    private static ActionEntry actionEntry;
    private long currentPot;
    private Properties prop = new Properties();

    public Roll() {
        actionID = (short) ModActions.getNextActionId();
        actionEntry = ActionEntry.createEntry(actionID, "Roll", "", new int[]{});
        ModActions.registerAction(actionEntry);
    }

    @Override
    public BehaviourProvider getBehaviourProvider() {
        return this;
    }

    @Override
    public ActionPerformer getActionPerformer() {
        return this;
    }

    @Override
    public short getActionId() {
        return actionID;
    }

    /**
     * @param performer performer representing the instantiation of Creature.
     * @param source    The Item source.
     * @param target    The Item target.
     * @return Fuck Warnings.
     **/
    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        if (source.getTemplateId() == 15000 && target.getTemplateId() == 15000) {
            return Collections.singletonList(actionEntry);
        } else {
            return null;
        }
    }

    /**
     * @param act       the act.
     * @param performer performer representing the instantiation of Creature.
     * @param source    The Item source.
     * @param target    The Item target.
     * @param action    Action ID number.
     * @param counter   Timer shit.
     * @return Fuck Warnings.
     **/
    @Override
    public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
        int winningNumber = Server.rand.nextInt(100) + 1;
        String potFile = "mods/GambleMod/pot.properties";
        String logFile = "mods/GambleMod/log.txt";
        if (source.getTemplateId() == 15000 && target.getTemplateId() == 15000) {
            try {
                long value = target.getData1();
                long initialPot = MonetaryConstants.COIN_SILVER;
                if (currentPot < initialPot) {
                    currentPot = initialPot;
                }
                InputStream input = new FileInputStream(potFile);
                prop.load(input);
                currentPot = value + Long.parseLong(prop.getProperty("Pot"));
                FileWriter fileWriter = new FileWriter(potFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("Pot=" + String.valueOf(currentPot));
                bufferedWriter.close();
                performer.getCommunicator().sendNormalServerMessage("Pot = " + String.valueOf(currentPot));
                Items.destroyItem(target.getWurmId());

                // The magic happens here.
                switch (winningNumber) {
                    case 25: {
                        performer.getCommunicator().sendNormalServerMessage("Rolled a " + winningNumber + ".");
                        performer.getCommunicator().sendNormalServerMessage("You won 25% of the pot");
                        currentPot = currentPot % -25;
                    }
                    case 50: {
                        performer.getCommunicator().sendNormalServerMessage("Rolled a " + winningNumber + ".");
                        performer.getCommunicator().sendNormalServerMessage("You won 50% of the pot");
                        currentPot = currentPot % -50;
                    }
                    case 75: {
                        performer.getCommunicator().sendNormalServerMessage("Rolled a " + winningNumber + ".");
                        performer.getCommunicator().sendNormalServerMessage("You won 75% of the pot");
                        currentPot = currentPot % -75;
                    }
                    case 100: {
                        performer.getCommunicator().sendNormalServerMessage("Rolled a " + winningNumber + ".");
                        performer.getCommunicator().sendNormalServerMessage("You won 100% of the pot");
                        currentPot = currentPot % -100;
                    }
                    default:
                        performer.getCommunicator().sendNormalServerMessage("You rolled a " + winningNumber + ", you win nothing.");
                }

                // Log Writer
                FileWriter writeLog = new FileWriter(logFile, true);
                BufferedWriter bufferedLogWriter = new BufferedWriter(writeLog);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date timeStamp = new Date();
                bufferedLogWriter.write("==========================================================\n");
                bufferedLogWriter.write(dateFormat.format(timeStamp) + "\n");
                bufferedLogWriter.write("" + performer.getName() + " rolled " + winningNumber + ".\n");
                bufferedLogWriter.write("Pot = " + currentPot + "\n");
                bufferedLogWriter.write("==========================================================\n");
                bufferedLogWriter.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            performer.getCommunicator().sendNormalServerMessage("Cant do that");
        }
        return true;
    }
}