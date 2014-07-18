package app.jaid.devrays.entity;

import app.jaid.devrays.Core;
import app.jaid.devrays.geo.Angle;
import app.jaid.devrays.geo.Point;
import app.jaid.devrays.physics.Colliding;
import app.jaid.jtil.JTil;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Main living {@link Entity}. Has a weapon to shot {@link Bullet} objects and can be hit by bullets.
 *
 * @author jaid
 */
public abstract class Mob implements Entity {

	protected Angle angle = new Angle();
	protected int healthPoints, maxHealthPoints;
	protected Point position;
	protected float speed;
	protected Team team;
	protected TextureRegion texture;

	public Mob(Point position)
	{
		this.position = position;
	}

	public Mob(Point position, Team team)
	{
		this(position);
		this.team = team;
	}

	public void damage(int damage)
	{
		healthPoints -= damage;
	};

	public abstract void die();

	public abstract float getBraking();

	@Override
	public Point getCenter()
	{
		return position.add(getWidth() / 2, getHeight() / 2);
	}

	@Override
	public float getHeight()
	{
		return texture.getRegionHeight() / 16;
	}

	@Override
	public abstract Colliding getHitbox();

	public int getHP()
	{
		return healthPoints;
	};

	public int getMaxHP()
	{
		return maxHealthPoints;
	};

	@Override
	public abstract String getName();

	@Override
	public Point getPosition()
	{
		return position;
	}

	public abstract float getSpeed();

	@Override
	public Team getTeam()
	{
		return team != null ? team : Team.OTHER;
	}

	@Override
	public float getWidth()
	{
		return texture.getRegionWidth() / 16;
	}

	public void heal(int amount)
	{
		healthPoints = Math.min(maxHealthPoints, healthPoints + amount);
	};

	private void moveByVelocity()
	{
		position.move(angle, speed * Core.delta);
		speed = JTil.normalize(speed, 5 * getBraking() * Core.delta);
	}

	public void push(Angle direction, float power)
	{
		if (speed == 0)
			angle.setTo(direction);
		else
			angle = angle.moveTo(direction, angle.getShortestRotateDirection(direction) * power * Core.delta * 5);

		speed = Math.max(speed, power);
	}

	@Override
	public void render()
	{
		Core.getBatch().draw(texture, position.x, position.y, getWidth(), getHeight());
	}

	@Override
	public abstract void renderShapes();

	@Override
	public abstract void renderText();

	public void teleport(Point newPosition)
	{
		position.set(newPosition);
	}

	public void teleportCenter(Point newPosition)
	{
		position.set(newPosition.subtract(getWidth() / 2, getHeight() / 2));
	}

	@Override
	public boolean update()
	{
		if (healthPoints <= 0)
		{
			die();
			return false;
		}

		if (getBraking() != 1)
			moveByVelocity();

		return true;
	}
}