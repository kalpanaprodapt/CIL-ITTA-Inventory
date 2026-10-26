import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { WEBHOOK_SUBSCRIPTION_LIFECYCLE_STAGE_LABELS } from './types';
import type { WebhookSubscription } from './types';

export const webhookSubscriptionColumns: Column<WebhookSubscription>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/integration/webhook-subscriptions/${row.id}`}>{row.code}</RouterLink>,
  },
  {
    key: 'name',
    header: 'Name',
    sortable: true,
    align: 'left',
    render: (row) => row.name ?? '—',
  },
  {
    key: 'description',
    header: 'Description',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.description, 60),
  },
  {
    key: 'status',
    header: 'Status',
    sortable: true,
    align: 'left',
    render: (row) => <StatusBadge status={row.status} />,
  },
  {
    key: 'externalRef',
    header: 'External Ref',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.externalRef ?? '—',
  },
  {
    key: 'callbackUrl',
    header: 'Callback Url',
    sortable: true,
    align: 'left',
    render: (row) => row.callbackUrl ?? '—',
  },
  {
    key: 'eventTypes',
    header: 'Event Types',
    sortable: true,
    align: 'left',
    render: (row) => row.eventTypes ?? '—',
  },
  {
    key: 'secretRef',
    header: 'Secret Ref',
    sortable: true,
    align: 'left',
    render: (row) => row.secretRef ?? '—',
  },
  {
    key: 'active',
    header: 'Active',
    sortable: true,
    align: 'left',
    render: (row) => <BooleanCell value={row.active} />,
  },
  {
    key: 'retryLimit',
    header: 'Retry Limit',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.retryLimit),
  },
  {
    key: 'labels',
    header: 'Labels',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.labels ?? '—',
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? WEBHOOK_SUBSCRIPTION_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
  {
    key: 'sourceSystem',
    header: 'Source System',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.sourceSystem ?? '—',
  },
  {
    key: 'region',
    header: 'Region',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.region ?? '—',
  },
  {
    key: 'owner',
    header: 'Owner',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.owner ?? '—',
  },
];

export const webhookSubscriptionDefaultVisible = webhookSubscriptionColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
