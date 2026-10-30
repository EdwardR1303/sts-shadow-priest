package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;

public class MadnessOverwhelming extends PriestCard {
    public static final String ID="shadowpriest:MadnessOverwhelming";
    public MadnessOverwhelming() { super(ID,1,CardType.SKILL,CardRarity.COMMON,CardTarget.ENEMY); baseMagicNumber=magicNumber=10; }
    @Override public float getTitleFontSize() { return 16f; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { int stacks=application();
        addToBot(new ApplyPowerAction(m,p,new MadnessPower(m,stacks),stacks));
        addToBot(new MakeTempCardInDiscardAction(new MindDecay(),1)); }
    @Override public void upgrade() { if(!upgraded) { upgradeName(); upgradeMagicNumber(5);upgradeText(); } }
    public int application() { return baseMagicNumber+(upgraded?8:5)*shadowpriest.mechanics.CombatPowerPlays.count(); }
    @Override public void applyPowers() { super.applyPowers(); magicNumber=application(); isMagicNumberModified=magicNumber!=baseMagicNumber; }
    @Override public void calculateCardDamage(AbstractMonster m) { super.calculateCardDamage(m); magicNumber=application(); isMagicNumberModified=magicNumber!=baseMagicNumber; }
}
