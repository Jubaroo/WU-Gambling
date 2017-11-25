package org.ausimus.wurmunlimited.mods.gambling.actions;

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

import com.wurmonline.server.items.*;
import org.ausimus.wurmunlimited.mods.gambling.config.AusConstants;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;
import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;

import java.util.Collections;
import java.util.List;

public class DebugMachine implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer
{
    private static short actionID;
    private static ActionEntry actionEntry;

    DebugMachine()
    {
        actionID = (short) ModActions.getNextActionId();
        actionEntry = ActionEntry.createEntry(actionID, "Get Debug Info", "-_-", new int[]{});
        ModActions.registerAction(actionEntry);
    }

    /**
     * {@inheritDoc
     *
     * @return
     */
    @Override
    public BehaviourProvider getBehaviourProvider()
    {
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public ActionPerformer getActionPerformer()
    {
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public short getActionId()
    {
        return actionID;
    }

    /**
     * {@inheritDoc}
     *
     * @param performer performer representing the instantiation of Creature.
     * @param source    The Item source.
     * @param target    The Item target.
     * @return {@link Collections#singletonList(java.lang.Object) object will = {@link DebugMachine#actionEntry} else is null.}.
     **/
    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target)
    {
        if (performer.getPower() == MiscConstants.POWER_IMPLEMENTOR &&
                target.getTemplateId() == AusConstants.GamblingMachineTemplateID)
        {
            return Collections.singletonList(actionEntry);
        }
        else
        {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param act       the act.
     * @param performer performer representing the instantiation of Creature.
     * @param source    The Item source.
     * @param target    The Item target.
     * @param action    Action ID number.
     * @param counter   Timer shit.
     * @return boolean.
     **/
    @Override
    public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter)
    {
        Item[] items = target.getAllItems(true);
        int index;
        if (performer.getPower() == MiscConstants.POWER_IMPLEMENTOR &&
                target.getTemplateId() == AusConstants.GamblingMachineTemplateID)
        {
            for (index = 0; index < items.length;)
            {
                if (items[index].getTemplateId() == AusConstants.GamblingTokenTemplateID)
                {
                    performer.getCommunicator().sendNormalServerMessage(
                            "Combined value of all internal tokens = " + items[index].getData1());
                }
            }
        }
        return true;
    }
}