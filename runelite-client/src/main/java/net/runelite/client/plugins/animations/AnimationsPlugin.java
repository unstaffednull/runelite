/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.animations;

import java.io.IOException;
import java.util.HashMap;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.AnimationChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.http.api.animation.AnimationsClient;
import org.apache.commons.lang3.ArrayUtils;

@PluginDescriptor(
	name = "Animations",
	hidden = true
)
@Slf4j
public class AnimationsPlugin extends Plugin
{
	private final AnimationsClient animationsClient = new AnimationsClient();

	private HashMap<Integer, int[]> animations;
	@Inject
	private Client client;

	{
		try
		{
			animations = animationsClient.get();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Subscribe
	private void onAnimationChanged(AnimationChanged event)
	{
		if (event.getActor() instanceof NPC)
		{
			if (event.getActor().getAnimation() != -1)
			{
				if (ArrayUtils.contains(animations.get(((NPC) event.getActor()).getId()), event.getActor().getAnimation()))
				{
					return;
				}
				int[] newAnimations = ArrayUtils.add(animations.get(((NPC) event.getActor()).getId()), event.getActor().getAnimation());
				animations.put(((NPC) event.getActor()).getId(), newAnimations);
				animationsClient.submit(((NPC) event.getActor()).getId(), event.getActor().getAnimation());
			}
		}
	}
}
