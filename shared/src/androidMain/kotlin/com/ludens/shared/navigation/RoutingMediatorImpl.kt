package com.ludens.shared.navigation

import com.ludens.shared.requirements.logW
import java.util.*

private val QUEUE_LOCK = Any()

class RoutingMediatorImpl : RoutingMediator {

    private val routingActionQueue = LinkedList<QueuedAction>()

    private var routingActionConsumer: RoutingActionConsumer? = null

    override fun dispatch(routingAction: (Router) -> Unit) = dispatchDistinct(null, routingAction)

    override fun dispatchDistinct(actionId: String?, routingAction: (Router) -> Unit) {
        synchronized(QUEUE_LOCK) {
            routingActionConsumer?.onRoutingAction(routingAction) ?: queueAction(routingAction, actionId)
        }
    }

    private fun queueAction(routingAction: (Router) -> Unit, actionId: String?) {
        actionId?.let { routingActionQueue.removeAll { queuedAction -> queuedAction.actionId == it } }
        routingActionQueue.add(QueuedAction(routingAction, actionId))
    }

    override fun setActiveRoutingActionConsumer(routingActionConsumer: RoutingActionConsumer) {
        if (this.routingActionConsumer != null) {
            logW("Setting action consumer before the previous one was unset")
        }

        synchronized(QUEUE_LOCK) {
            this.routingActionConsumer ?: flushRoutingActions(routingActionConsumer)
            this.routingActionConsumer = routingActionConsumer
        }
    }

    private fun flushRoutingActions(routingActionConsumer: RoutingActionConsumer) {
        while (routingActionQueue.peek() != null) {
            routingActionConsumer.onRoutingAction(routingActionQueue.poll().routingAction)
        }
    }

    override fun unsetRoutingActionConsumer(routingActionConsumer: RoutingActionConsumer) {
        if (this.routingActionConsumer !== routingActionConsumer) {
            logW("Can't unset foreign consumer")
            return
        }
        this.routingActionConsumer = null
    }

    class QueuedAction(val routingAction: (Router) -> Unit, val actionId: String? = null)
}
