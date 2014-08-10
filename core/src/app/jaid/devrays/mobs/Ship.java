package app.jaid.devrays.mobs;

import app.jaid.devrays.entity.*;
import app.jaid.devrays.geo.Point;
import app.jaid.devrays.geo.Rect;
import app.jaid.devrays.items.Inventory;
import app.jaid.devrays.items.Weapon;
import app.jaid.devrays.physics.Colliding;

import com.badlogic.gdx.utils.Array;

/**
 * Special kind of mobs whose instances are either controlled by a human (see {@link Player}) or are NPCs who have the
 * same conditions as players.
 *
 * @author jaid
 */
public abstract class Ship extends Mob {

	protected Inventory inventory = new Inventory();

	public Ship(Point position, Team team)
	{
		super(position, team);
	}

	@Override
	public void die()
	{

	}

	public Array<Weapon> getArsenal()
	{
		return inventory.equipment.arsenal;
	}

	@Override
	public float getBraking()
	{
		return 0.3f * BRAKING_FACTOR;
	}

	@Override
	public Colliding getHitbox()
	{
		return new Rect(position, getWidth(), getHeight());
	}

	@Override
	public String getName()
	{
		return "Ship from Team " + getTeam();
	}

	@Override
	public float getSpeed()
	{
		return 10f;
	}

	@Override
	public Weapon getWeapon()
	{
		return inventory.equipment.arsenal.get(selectedWeapon);
	}

	public void lastWeapon()
	{
		if (selectedWeapon == 0)
			selectedWeapon = inventory.equipment.arsenal.size - 1;
		else
			selectedWeapon = selectedWeapon - 1;
	}

	public void nextWeapon()
	{
		if (selectedWeapon + 1 == inventory.equipment.arsenal.size)
			selectedWeapon = 0;
		else
			selectedWeapon = selectedWeapon + 1;
	}

	@Override
	public void renderShapes()
	{
	}

	@Override
	public void renderText()
	{
		// Drawer.drawTextOnWorld(getName(), position);
	}

}
