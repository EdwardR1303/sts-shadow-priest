package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.legendary.LegendaryRarity;
public abstract class LegendaryCard extends PriestCard {
 protected LegendaryCard(String id){super(id,-2,CardType.POWER,LegendaryRarity.LEGENDARY,CardTarget.SELF);shadowpriest.legendary.LegendaryArt.apply(this); }
 @Override public boolean canUse(AbstractPlayer p,AbstractMonster m){cantUseMessage="This Legendary equips automatically once at combat start.";return false;}
 @Override public void use(AbstractPlayer p,AbstractMonster m){} // Temporary copies and forced plays cannot equip another weapon.
 public abstract void equip(AbstractPlayer p);
}
