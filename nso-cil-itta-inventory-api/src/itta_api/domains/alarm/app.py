"""FastAPI application for the Alarms and Events domain."""
from itta_core.app import create_app
from itta_core.dependencies import ServiceRegistry

from itta_api.domains.alarm.alarm_definition.client import AlarmDefinitionClient
from itta_api.domains.alarm.alarm_definition.service import AlarmDefinitionService
from itta_api.domains.alarm.alarm_instance.client import AlarmInstanceClient
from itta_api.domains.alarm.alarm_instance.service import AlarmInstanceService
from itta_api.domains.alarm.event_source.client import EventSourceClient
from itta_api.domains.alarm.event_source.service import EventSourceService
from itta_api.domains.alarm.suppression_rule.client import SuppressionRuleClient
from itta_api.domains.alarm.suppression_rule.service import SuppressionRuleService
from itta_api.domains.alarm.ticket_link.client import TicketLinkClient
from itta_api.domains.alarm.ticket_link.service import TicketLinkService
from itta_api.domains.alarm.alarm_definition.router import router as alarm_definition_router
from itta_api.domains.alarm.alarm_instance.router import router as alarm_instance_router
from itta_api.domains.alarm.event_source.router import router as event_source_router
from itta_api.domains.alarm.suppression_rule.router import router as suppression_rule_router
from itta_api.domains.alarm.ticket_link.router import router as ticket_link_router

DOMAIN = "alarm"
UPSTREAM_ENV = "INVENTORY_ALARM_URL"


def build_registry(registry: ServiceRegistry) -> None:
    registry.register("alarm_definition", lambda up, cache: AlarmDefinitionService(AlarmDefinitionClient(up), cache))
    registry.register("alarm_instance", lambda up, cache: AlarmInstanceService(AlarmInstanceClient(up), cache))
    registry.register("event_source", lambda up, cache: EventSourceService(EventSourceClient(up), cache))
    registry.register("suppression_rule", lambda up, cache: SuppressionRuleService(SuppressionRuleClient(up), cache))
    registry.register("ticket_link", lambda up, cache: TicketLinkService(TicketLinkClient(up), cache))


def create() -> "FastAPI":
    app = create_app(title="ITTA Inventory API - Alarms and Events", domain=DOMAIN, upstream_env=UPSTREAM_ENV,
                     registry_builder=build_registry)
    app.include_router(alarm_definition_router)
    app.include_router(alarm_instance_router)
    app.include_router(event_source_router)
    app.include_router(suppression_rule_router)
    app.include_router(ticket_link_router)
    return app


app = create()
