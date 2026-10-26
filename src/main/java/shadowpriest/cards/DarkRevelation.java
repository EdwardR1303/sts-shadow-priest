package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
import shadowpriest.actions.GainInsanityAction;

public class DarkRevelation extends PriestCard {
    public static final String ID="shadowpriest:DarkRevelation";
    public DarkRevelation() { super(ID,1,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);exhaust=true; }
    @Override public void use(AbstractPlayer p,AbstractMonster m) { addToBot(new shadowpriest.actions.DarkRevelationAction()); }
    @Override public void upgrade() { if(!upgraded) { upgradeName();upgradeBaseCost(0); } }

}
