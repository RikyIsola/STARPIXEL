package com.island.starpixel.blocchi.solidi.mobili.ai;
import com.island.starpixel.blocchi.solidi.mobili.*;
public abstract class Ai extends Mobile
{
	public double velocita()
	{
		return 0.05;
	}
	public int vita()
	{
		return 1;
	}
}
