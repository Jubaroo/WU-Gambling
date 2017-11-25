/*
Author Ausimus 2016-2017.
Confidential Code including all sub-code herein.
If you see this contact Ausimus with the name of the person that gave it to you.
 ________  ________  ___           ___    ___ ________  _________  ________
|\   ____\|\   __  \|\  \         |\  \  /  /|\   ____\|\___   ___\\   __  \
\ \  \___|\ \  \|\  \ \  \        \ \  \/  / | \  \___|\|___ \  \_\ \  \|\  \
 \ \  \    \ \   __  \ \  \        \ \    / / \ \_____  \   \ \  \ \ \  \\\  \
  \ \  \____\ \  \ \  \ \  \____    \/  /  /   \|____|\  \   \ \  \ \ \  \\\  \
   \ \_______\ \__\ \__\ \_______\__/  / /       ____\_\  \   \ \__\ \ \_______\
    \|_______|\|__|\|__|\|_______|\___/ /       |\_________\   \|__|  \|_______|
                                 \|___|/        \|_________|
*/

package com.wurmonline.server.questions;

import com.wurmonline.server.Server;
import com.wurmonline.server.Servers;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.economy.Change;
import com.wurmonline.server.economy.Economy;
import java.util.Properties;

import static com.wurmonline.server.questions.AusBankWithDraw.AusparseWithdrawMoneyQuestion;

public final class AusWithdrawMoneyQuestion extends Question
{

    /**
     * {@inheritDoc}
     *
     * @param aResponder
     * @param aTitle
     * @param aQuestion
     * @param aTarget
     */
    AusWithdrawMoneyQuestion(Creature aResponder, String aTitle, String aQuestion, long aTarget)
    {
        super(aResponder, aTitle, aQuestion, 36, aTarget);
    }

    /**
     * {@inheritDoc}
     *
     * @param answers
     */
    public void answer(Properties answers)
    {
        this.setAnswer(answers);
        AusparseWithdrawMoneyQuestion(this);
    }

    public void sendQuestion()
    {
        StringBuilder buf = new StringBuilder();
        buf.append(this.getBmlHeader());
        this.fillDialogText(buf);
        buf.append(this.createAnswerButton2());
        this.getResponder().getCommunicator().sendBml(300, 300,
                true, true, buf.toString(), 200, 200, 200, this.title);
    }

    /**
     * {@inheritDoc}
     *
     * @param buf
     */
    private void fillDialogText(StringBuilder buf)
    {
        long money = this.getResponder().getMoney();
        if(!Server.getInstance().isPS() && (Servers.localServer.entryServer
                || getResponder().getPower() > 0) && !Servers.localServer.testServer)
        {
            buf.append("text{text=\'You are not allowed to withdraw money on this server since it will be " +
                    "lost when you use a portal.\'}");
        }
        else if(money <= 0L)
        {
            buf.append("text{text=\'You have no money in the bank.\'}");
        }
        else
        {
            Change change = Economy.getEconomy().getChangeFor(money);
            buf.append("text{text=\'You may withdraw up to ").append(change.getChangeString()).append(".\'}");
            buf.append("text{text=\'The money will end up in your inventory.\'}");
            long gold = change.getGoldCoins();
            long silver = change.getSilverCoins();
            long copper = change.getCopperCoins();
            long iron = change.getIronCoins();
            if(money >= 1000000L)
            {
                buf.append("harray{input{text=\'0\'; id=\'gold\'; maxchars=\'10\'}label" +
                        "{text=\'(").append(gold).append(") Gold coins\'}}");
            }

            if(money >= 10000L)
            {
                buf.append("harray{input{text=\'0\'; id=\'silver\'; maxchars=\'10\'}" +
                        "label{text=\'(").append(silver).append(") Silver coins\'}}");
            }

            if(money >= 100L)
            {
                buf.append("harray{input{text=\'0\'; id=\'copper\'; maxchars=\'10\'}label" +
                        "{text=\'(").append(copper).append(") Copper coins\'}}");
            }

            buf.append("harray{input{text=\'0\'; id=\'iron\'; maxchars=\'10\'}label" +
                    "{text=\'(").append(iron).append(") Iron coins\'}}");
        }
    }
}
