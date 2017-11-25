package com.wurmonline.server.questions;
import com.wurmonline.server.Items;
import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.NoSuchItemException;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.economy.Change;
import com.wurmonline.server.economy.Economy;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.items.ItemTypes;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
public class AusBankWithDraw implements WurmServerMod, ItemTypes, MiscConstants,
        ModAction, BehaviourProvider, ActionPerformer
{

    private static short actionABW;
    private static ActionEntry actionEntryABW;

    public AusBankWithDraw()
    {
        actionABW = (short) ModActions.getNextActionId();
        actionEntryABW = ActionEntry.createEntry(actionABW, "Bank Withdraw", "Windrawing", new int[]{
        });
        ModActions.registerAction(actionEntryABW);
    }

    /**
     * {@inheritDoc}
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
        return actionABW;
    }

    /**
     * {@inheritDoc}
     *
     * @param performer
     * @param source
     * @param target
     * @return
     */
    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target)
    {
        return getBehavioursFor(performer, target);
    }

    /**
     * {@inheritDoc}
     *
     * @param performer
     * @param target
     * @return
     */
    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item target)
    {
        if (target.getTemplateId() == ItemList.bodyBody)
        {
                return Collections.singletonList(actionEntryABW);
        }
        else
        {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param act
     * @param performer
     * @param source
     * @param target
     * @param action
     * @param counter
     * @return
     */
    @Override
    public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter)
    {
        return action(act, performer, target, action, counter);
    }

    /**
     * {@inheritDoc}
     *
     * @param act
     * @param performer
     * @param target
     * @param action
     * @param counter
     * @return
     */
    @Override
    public boolean action(Action act, Creature performer, Item target, short action, float counter)
    {
        if (target.getTemplateId() == ItemList.bodyBody)
        {
            sendWithdrawMoneyQuestion(performer, target);
        }
        else
        {
            performer.getCommunicator().sendNormalServerMessage("Right click your body.");
        }
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param performer
     * @param target
     */
    private static void sendWithdrawMoneyQuestion(Creature performer, Item target)
    {
        AusWithdrawMoneyQuestion dq = new AusWithdrawMoneyQuestion(performer, "Withdraw money", "Withdraw selected amount:", target.getWurmId());
        dq.sendQuestion();
    }

    /**
     * {@inheritDoc}
     *
     * @param question
     */
    static void AusparseWithdrawMoneyQuestion(AusWithdrawMoneyQuestion question)
    {
        Creature responder = question.getResponder();
        if(responder.isDead())
        {
            responder.getCommunicator().sendNormalServerMessage("You are dead, and may not withdraw any money.");
        }
        else
        {
            int type = question.getType();
            {
                if(type == 36)
                {
                    try
                    {
                        Item money = Items.getItem(question.getTarget());
                        if(!responder.isWithinDistanceTo(money.getPosX(), money.getPosY(), money.getPosZ(), 30.0F))
                        {
                            responder.getCommunicator().sendNormalServerMessage("You are too far away from the bank.");
                            return;
                        }
                    }
                    catch (NoSuchItemException var12)
                    {
                        responder.getCommunicator().sendNormalServerMessage("The bank no longer is available as the token is gone.");
                        return;
                    }

                    long var13 = responder.getMoney();
                    if(var13 > 0L)
                    {
                        long valueWithdrawn = getValueWithdrawn(question);
                        if(valueWithdrawn > 0L)
                        {
                            try
                            {
                                if(responder.chargeMoney(valueWithdrawn))
                                {
                                    Item[] iox = Economy.getEconomy().getCoinsFor(valueWithdrawn);
                                    Item inventory = responder.getInventory();

                                    for (Item anIox : iox)
                                    {
                                        inventory.insertItem(anIox);
                                    }

                                    Change var14 = Economy.getEconomy().getChangeFor(valueWithdrawn);
                                    responder.getCommunicator().sendNormalServerMessage("You withdraw " +
                                            var14.getChangeString() + " from the bank.");
                                    Change c = new Change(var13 - valueWithdrawn);
                                    responder.getCommunicator().sendNormalServerMessage("New balance: "
                                            + c.getChangeString() + ".");
                                }
                                else
                                {
                                    responder.getCommunicator().sendNormalServerMessage("You can not withdraw that" +
                                            " amount of money at the moment.");
                                }
                            }
                            catch (IOException ex)
                            {
                                responder.getCommunicator().sendNormalServerMessage("The transaction failed. Please " +
                                        "contact the game masters using the <i>/dev</i> command.");
                            }
                        }
                        else
                        {
                            responder.getCommunicator().sendNormalServerMessage("No money withdrawn.");
                        }
                    }
                    else
                    {
                        responder.getCommunicator().sendNormalServerMessage("You have no money in the bank.");
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param question
     * @return
     */
    private static long getValueWithdrawn(Question question)
    {
        String golds = question.getAnswer().getProperty("gold");
        String silvers = question.getAnswer().getProperty("silver");
        String coppers = question.getAnswer().getProperty("copper");
        String irons = question.getAnswer().getProperty("iron");

        try
        {
            long nfe = 0L;
            if(golds != null && golds.length() > 0)
            {
                nfe = Long.parseLong(golds);
            }

            long wantedSilver = 0L;
            if(silvers != null && silvers.length() > 0)
            {
                wantedSilver = Long.parseLong(silvers);
            }

            long wantedCopper = 0L;
            if(coppers != null && coppers.length() > 0)
            {
                wantedCopper = Long.parseLong(coppers);
            }

            long wantedIron = 0L;
            if(irons != null && irons.length() > 0)
            {
                wantedIron = Long.parseLong(irons);
            }

            if(nfe < 0L)
            {
                question.getResponder().getCommunicator().sendNormalServerMessage("You may not withdraw a " +
                        "negative amount of gold coins!");
                return 0L;
            }
            else if(wantedSilver < 0L)
            {
                question.getResponder().getCommunicator().sendNormalServerMessage("You may not withdraw a" +
                        " negative amount of silver coins!");
                return 0L;
            }
            else if(wantedCopper < 0L)
            {
                question.getResponder().getCommunicator().sendNormalServerMessage("You may not withdraw a " +
                        "negative amount of copper coins!");
                return 0L;
            }
            else if(wantedIron < 0L)
            {
                question.getResponder().getCommunicator().sendNormalServerMessage("You may not withdraw a " +
                        "negative amount of iron coins!");
                return 0L;
            }
            else
            {
                long valueWithdrawn = 1000000L * nfe;
                valueWithdrawn += 10000L * wantedSilver;
                valueWithdrawn += 100L * wantedCopper;
                valueWithdrawn += wantedIron;
                return valueWithdrawn;
            }
        }
        catch (NumberFormatException ex)
        {
            question.getResponder().getCommunicator().sendNormalServerMessage("The values were incorrect.");
            return 0L;
        }
    }
}