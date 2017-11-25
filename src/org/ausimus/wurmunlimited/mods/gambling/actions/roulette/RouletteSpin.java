package org.ausimus.wurmunlimited.mods.gambling.actions.roulette;

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

import com.wurmonline.server.*;
import com.wurmonline.server.items.*;
import org.ausimus.wurmunlimited.mods.gambling.config.AusConstants;
import org.ausimus.wurmunlimited.mods.gambling.dataStream;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;

import java.io.IOException;
import java.util.Collections;

import java.util.List;
import java.util.Random;

public class RouletteSpin implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer {
    private static short actionID;
    private static ActionEntry actionEntry;

    public RouletteSpin() {
        actionID = (short) ModActions.getNextActionId();
        actionEntry = ActionEntry.createEntry(actionID, "Spin", "Spinning", new int[]{});
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
     * @return {@link Collections#singletonList(java.lang.Object) object will = {@link RouletteSpin#actionEntry} else is null.}.
     **/
    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        if (source.getTemplateId() == ItemList.bodyHand && target.getTemplateId() == AusConstants.GamblingMachineTemplateID && target.getAuxData() == AusConstants.GameModeRoulette) {
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
     * @return boolean.
     **/
    @Override
    public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
        Random rand = new Random();
        int randomNumberPick = rand.nextInt(37);
        int randomColor = rand.nextInt(2);

        if (target.getData2() == -1 && target.getColor() == -1) {
            performer.getCommunicator().sendNormalServerMessage("The token has no bet data.");
            return true;
        }

        try {
            if (source.getTemplateId() == ItemList.bodyHand && target.getTemplateId() == AusConstants.GamblingMachineTemplateID && target.getAuxData() == AusConstants.GameModeRoulette) {

                // Single
                if (target.getColor() == AusConstants.Black || target.getColor() == AusConstants.White) {
                    switch (randomColor) {
                        case 0:
                            if (target.getColor() == AusConstants.Black) {
                                performer.getCommunicator().sendNormalServerMessage("You Win you rolled black.");
                                performer.setMoney(performer.getMoney() + target.getData1());
                            }
                        case 1:
                            if (target.getColor() == AusConstants.White && randomColor == 1) {
                                performer.getCommunicator().sendNormalServerMessage("You Win you rolled white.");
                                performer.setMoney(performer.getMoney() + target.getData1());
                            }
                        default:
                            if (target.getColor() == AusConstants.White && randomColor == 0) {
                                performer.getCommunicator().sendNormalServerMessage("You Lose you rolled black.");
                            }
                            if (target.getColor() == AusConstants.Black && randomColor == 1) {
                                performer.getCommunicator().sendNormalServerMessage("You Lose you rolled white.");
                            }
                    }
                }

                // Double
                if (target.getData2() >= 0) {
                    if (randomNumberPick == target.getData2()) {
                        performer.getCommunicator().sendNormalServerMessage("You win, you rolled " + randomNumberPick + ".");
                        performer.setMoney(performer.getMoney() + dataStream.getData(target) / 2);
                        dataStream.writeData(target, dataStream.getData(target) / 2);
                    } else {
                        performer.getCommunicator().sendNormalServerMessage("You did not win the number, the random # was " + randomNumberPick + ".");
                    }
                }

                // Give em the dataStream
                if (target.getColor() != -1 && target.getData2() >= 0) {
                    if (randomNumberPick == target.getData2() && target.getColor() == AusConstants.Black && randomColor == 0) {
                        performer.getCommunicator().sendNormalServerMessage("You won the color and the number.");
                        performer.setMoney(performer.getMoney() + dataStream.getData(target));
                        dataStream.writeData(target, 0);
                    }
                    if (randomNumberPick == target.getData2() && target.getColor() == AusConstants.White && randomColor == 1) {
                        performer.getCommunicator().sendNormalServerMessage("You won the color and the number.");
                        performer.setMoney(performer.getMoney() + dataStream.getData(target));
                        dataStream.writeData(target, 0);
                    }
                }

                // Reset Bet Value
                target.setData1(-1);
            } else {
                performer.getCommunicator().sendNormalServerMessage("Cant do that");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;
    }
}