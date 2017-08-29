package com.island.starpixel;
import android.graphics.*;
import android.view.*;
import com.island.*;
import java.io.*;
import java.util.*;
public class Banca extends Finestra
{
	private Lan lan;
	private Gruppo g,attuale;
	private Gruppo[]mappe=new Gruppo[schermo().blocchi.dimensioni];
	boolean finito;
	private int ymin,xmin;
	private Gioco gioco;
	Banca(Gioco gioco,Lan lan)
	{
		super(gioco.schermo());
		this.gioco=gioco;
		g=new Gruppo(this,10,10);
		for(int a=0;a<mappe.length;a++)
		{
			mappe[a]=new Gruppo(g,0,1,10,10,10,10);
			mappe[a].immagine(schermo().blocchi.sfondoDimensione(a));
			if(a==0)attuale=mappe[a];
			else mappe[a].setVisibility(View.INVISIBLE);
		}
		for(int a=0;a<mappe.length;a++)new Bottone(g,10.0*a/mappe.length,1,10.0*(a+1)/mappe.length,2).scrivi(schermo().blocchi.dimensione(a)).setOnClickListener(dimensione(a));
		new Bottone(g,3,0,7,1).antiTrans(true,true).setOnClickListener(torna());
		new Bottone(g,7,0,10,1).scrivi("+").antiTrans(true,true).setOnClickListener(piu());
		new Bottone(g,0,0,3,1).scrivi("-").antiTrans(true,true).setOnClickListener(meno());
		g.aggiorna();
		this.lan=lan;
	}
	void aggiungi(final int x,final int y,final int dimensione,final String stato)
	{
		schermo().runOnUiThread(new Runnable()
			{
				public void run()
				{
					Gruppo mappa=mappe[dimensione];
					new Testo(mappa,x,y,x+1,y+1,x+" "+y)
					{
						public void onDraw(Canvas c)
						{
							Paint p=schermo().paint;
							if(gioco.tu!=null&&x==gioco.tu.chunk.x&&y==gioco.tu.chunk.y)
							{
								p.setColor(Color.YELLOW);
								c.drawRect(0,0,c.getWidth(),c.getHeight(),p);
							}
							else if(stato.equals(gioco.schermo().blocchi.statoAlleati))
							{
								p.setColor(Color.BLUE);
								c.drawRect(0,0,c.getWidth(),c.getHeight(),p);
							}
							else if(stato.equals(gioco.schermo().blocchi.statoVita))
							{
								p.setColor(Color.GREEN);
								c.drawRect(0,0,c.getWidth(),c.getHeight(),p);
							}
							else if(stato.equals(gioco.schermo().blocchi.statoNemici))
							{
								p.setColor(Color.RED);
								c.drawRect(0,0,c.getWidth(),c.getHeight(),p);
							}
							else if(stato.equals(gioco.schermo().blocchi.statoQualcosa))
							{
								p.setColor(Color.LTGRAY);
								c.drawRect(0,0,c.getWidth(),c.getHeight(),p);
							}
							else if(stato.equals(gioco.schermo().blocchi.statoNebula))
							{
								p.setColor(Color.rgb(143,0,255));
								c.drawRect(0,0,c.getWidth(),c.getHeight(),p);
							}
							else if(stato.equals(gioco.schermo().blocchi.statoNero))
							{
								p.setColor(Color.BLACK);
								c.drawRect(0,0,c.getWidth(),c.getHeight(),p);
							}
							else if(stato.equals(gioco.schermo().blocchi.statoBoss))
							{
								p.setColor(Color.rgb(150,0,0));
								c.drawRect(0,0,c.getWidth(),c.getHeight(),p);
							}
							else if(stato.equals(gioco.schermo().blocchi.statoZombie))
							{
								p.setColor(Color.rgb(0,150,0));
								c.drawRect(0,0,c.getWidth(),c.getHeight(),p);
							}
							else if(stato.equals(gioco.schermo().blocchi.statoGemma))
							{
								p.setColor(Color.rgb(175,238,238));
								c.drawRect(0,0,c.getWidth(),c.getHeight(),p);
							}
							else if(stato.equals(gioco.schermo().blocchi.statoQuasispazio))
							{
								p.setColor(Color.rgb(100,255,100));
								c.drawRect(0,0,c.getWidth(),c.getHeight(),p);
							}
							else
							{
								p.setColor(Color.BLUE);
								c.drawRect(0,0,c.getWidth(),c.getHeight()/10,p);
								c.drawRect(0,0,c.getWidth()/10,c.getHeight(),p);
								c.drawRect(c.getWidth()-c.getWidth()/10,0,c.getWidth(),c.getHeight(),p);
								c.drawRect(0,c.getHeight()-c.getHeight()/10,c.getWidth(),c.getHeight(),p);
							}
							super.onDraw(c);
						}
					}.larghezzaX(0.6).carattere(0.6);
					if(x<xmin)
					{
						xmin=x;
						schermo().toast(""+xmin);
					}
					if(y<ymin)ymin=y;
				}
			});
	}
	void fine()
	{
		finito=true;
		schermo().runOnUiThread(new Runnable()
		{
			public void run()
			{
				toast("fine "+gioco.tu.chunk.x+" "+gioco.tu.chunk.y+" "+xmin);
				for(Gruppo mappa:mappe)for(int a=0;a<mappa.getChildCount();a++)
					{
						Oggetto o=(Oggetto)mappa.getChildAt(a);
						if(!o.antiTransX())
						{
							o.x(o.x()-xmin);
							o.y(o.y()-ymin);
						}
					}
				torna().onClick(null);
				g.aggiorna();
			}
		});
	}
	public MainActivity schermo()
	{
		return(MainActivity)super.schermo();
	}
	public void onBackPressed()
	{
		if(finito)super.onBackPressed();
	}
	public void sempreGrafico()
	{
		attuale.sempre(0.01);
	}
	private View.OnClickListener torna()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				dimensione(gioco.tu.chunk.dimensione).onClick(null);
				attuale.trans(gioco.tu.chunk.x-xmin+0.5-attuale.maxX()/2,gioco.tu.chunk.y-ymin+0.5-attuale.maxY()/2);
				attuale.aggiorna();
			}
		};
	}
	private View.OnClickListener dimensione(final int n)
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				attuale.setVisibility(View.INVISIBLE);
				attuale=mappe[n];
				attuale.setVisibility(View.VISIBLE);
				g.aggiorna();
			}
		};
	}
	private View.OnClickListener piu()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				if(attuale.maxX()>1)attuale.max(attuale.maxX()-1,attuale.maxY()-1);
				attuale.aggiorna();
			}
		};
	}
	private View.OnClickListener meno()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				attuale.max(attuale.maxX()+1,attuale.maxY()+1);
				attuale.aggiorna();
			}
		};
	}
}
