package com.island.starpixel;
import android.view.*;
import com.island.*;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.solidi.*;
import com.island.starpixel.blocchi.solidi.armi.*;
import com.island.starpixel.blocchi.solidi.mobili.*;
import com.island.starpixel.blocchi.terreni.*;
import java.util.*;
import android.graphics.*;
public class ChunkLan extends Gruppo
{
	int x,y,luce,dimensione,immagine;
	Lista<EntitaLan>entita=new Lista<EntitaLan>();
	Gioco gioco;
	String stato;
	Oggetto copertura;
	boolean scudi;
	private Oggetto centro;
	ChunkLan(Gruppo g,int x,int y,String stato,final Gioco gioco,int dimensione)
	{
		super(g,0,1,10,10,((MainActivity)g.schermo()).blocchi.vista,((MainActivity)g.schermo()).blocchi.vista);
		this.gioco=gioco;
		this.dimensione=dimensione;
		stato(stato);
		antiScroll(true,true);
		this.x=x;
		this.y=y;
		centro=new Oggetto(this,0,0,1,1).immagine(R.drawable.centro);
		schermo().runOnUiThread(new Runnable()
		{
			public void run()
			{
				gioco.superordine=true;
			}
		});
	}
	EntitaLan trovaSolido(double x,double y)
	{
		for(EntitaLan e:entita)if(e.b instanceof Solido&&e.toccandoBase(x,y,x,y))return e;
		return null;
	}
	EntitaLan trovaTeletrasporto(double x,double y)
	{
		for(EntitaLan e:entita)if(e.b instanceof Teletrasporto&&e.toccandoBaseUguale(x,y,x,y))return e;
		return null;
	}
	EntitaLan trovaArma(double x,double y)
	{
		for(EntitaLan e:entita)if(e.b instanceof Arma&&e.toccandoBaseUguale(x,y,x,y))return e;
		return null;
	}
	void sempre()
	{
		int[]vecchi=new int[getChildCount()];
		boolean visibile=vecchi.length!=0;
		for(int a=0;a<vecchi.length;a++)if(getChildAt(a)instanceof EntitaLan)
		{
			EntitaLan e=(EntitaLan)getChildAt(a);
			vecchi[a]=e.luce;
			e.luce=luce;
			if(visibile&&e.x()==0&&e.y()==0)visibile=false;
			if(e.b instanceof Solido&&!(e.b instanceof Mobile)&&e.coperto==null)for(int b=0;b<vecchi.length;b++)if(getChildAt(b)instanceof EntitaLan)
			{
				final EntitaLan e1=(EntitaLan)getChildAt(b);
				if(e1.b instanceof Terreno&&e1.x()==e.x()&&e1.y()==e.y())
				{
					e.coperto=e1;
					e.concatena(e.immagine());
					e.immagine(e1.b.immagine());
					schermo().runOnUiThread(new Runnable()
					{
						public void run()
						{
							e1.setVisibility(View.INVISIBLE);
						}
					});
					break;
				}
			}
		}
		for(EntitaLan e:entita)if(e.luminosita>0)for(int a=0;a<vecchi.length;a++)if(getChildAt(a)instanceof EntitaLan)
					{
						EntitaLan e1=(EntitaLan)getChildAt(a);
						e1.luce+=Oggetto.zero(e.luminosita-e.distanzaImprecisa(e1)*e.riduzione);
						if(e1.luce>=255)e1.luce=255;
					}
		for(int a=0;a<vecchi.length;a++)if(getChildAt(a)instanceof EntitaLan)
		{
			EntitaLan e=(EntitaLan)getChildAt(a);
			if(e.luce!=vecchi[a])
			{
				e.colore(Color.argb(255-e.luce,0,0,0));
				e.postInvalidate();
			}
		}
		final int finale;
		if(visibile)finale=View.VISIBLE;
		else finale=View.INVISIBLE;
		schermo().runOnUiThread(new Runnable()
		{
			public void run()
			{
				if(centro.getVisibility()!=finale)centro.setVisibility(finale);
			}
		});
	}
	void stato(String stato)
	{
		Blocchi b=gioco.schermo().blocchi;
		this.stato=stato;
		immagine=gioco.schermo().blocchi.sfondoDimensione(dimensione);
		if(stato.equals(b.statoQuasispazio))
		{
			if(immagine==R.drawable.sfondoquasispazio)immagine=R.drawable.sfondo;
			else immagine=R.drawable.sfondoquasispazio;
		}
		else if(stato.equals(b.statoNebula))immagine=R.drawable.sfondopieno;
		else if(stato.equals(b.statoNero))immagine=0;
	}
	public Gruppo immagine(int immagine)
	{
		return this;
	}
	public Gruppo colore(int colore)
	{
		return this;
	}
}
