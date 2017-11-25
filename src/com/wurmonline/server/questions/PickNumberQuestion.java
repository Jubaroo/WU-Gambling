package com.wurmonline.server.questions;

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

import com.wurmonline.server.Items;
import com.wurmonline.server.NoSuchItemException;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.shared.constants.ItemMaterials;
import java.util.Properties;

public final class PickNumberQuestion extends Question implements ItemMaterials
{

    /**
     * {@inheritDoc}
     *
     * @param aResponder
     * @param aTitle
     * @param aQuestion
     * @param aTarget
     */
    public PickNumberQuestion(Creature aResponder, String aTitle, String aQuestion, long aTarget)
    {
        super(aResponder, aTitle, aQuestion, 4, aTarget);
    }

    public void sendQuestion()
    {
        StringBuilder buf = new StringBuilder(this.getBmlHeader());
        int width = 150;
        int height = 150;
        try
        {
            Item target = Items.getItem(this.target);
            int data2 = target.getData2();
            if (target.hasData())
            {
                buf.append("harray{input{id='data2'; maxchars='20'; text='").append(data2).append("'}label{text='Roulette Number'}}");
            }
        }
        catch (NoSuchItemException ex)
        {
            ex.printStackTrace();
        }

        buf.append(this.createAnswerButton2());
        this.getResponder().getCommunicator().sendBml(width, height, true, true, buf.toString(), 0, 0, 255, this.title);
    }

    /**
     * {@inheritDoc}
     *
     * @param answers
     */
    public void answer(Properties answers)
    {
        this.setAnswer(answers);
        parseItemDataQuestion(this);
    }

    /**
     * {@inheritDoc}
     *
     * @param question
     */
    private void parseItemDataQuestion(PickNumberQuestion question)
    {
        Creature performer = this.getResponder();
        long target = question.getTarget();
        String d2 = question.getAnswer().getProperty("data2");
        try
        {
            Item item = Items.getItem(target);
            int data2 = d2 == null ? -1 : Integer.parseInt(d2);
            if (item.hasData())
            {
                if (data2 >= 0 && data2 <= 37)
                {
                    item.setData2(data2);
                    performer.getCommunicator().sendNormalServerMessage("You choose " + data2 + ".");
                }
                else
                {
                    performer.getCommunicator().sendNormalServerMessage("The value must be greater than or equal 0 and less than or equal 37.");
                }
            }
        }
        catch (NoSuchItemException ex)
        {
            ex.printStackTrace();
        }
    }
}