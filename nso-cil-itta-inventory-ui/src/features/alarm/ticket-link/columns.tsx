import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { TICKET_LINK_TICKET_SYSTEM_LABELS, TICKET_LINK_CRITICALITY_LABELS } from './types';
import type { TicketLink } from './types';

export const ticketLinkColumns: Column<TicketLink>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/alarm/ticket-links/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'alarmId',
    header: 'Alarm Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.alarmId} />,
  },
  {
    key: 'ticketSystem',
    header: 'Ticket System',
    sortable: true,
    align: 'left',
    render: (row) => row.ticketSystem ? TICKET_LINK_TICKET_SYSTEM_LABELS[row.ticketSystem] : '—',
  },
  {
    key: 'ticketNumber',
    header: 'Ticket Number',
    sortable: true,
    align: 'left',
    render: (row) => row.ticketNumber ?? '—',
  },
  {
    key: 'linkedAt',
    header: 'Linked At',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.linkedAt),
  },
  {
    key: 'owner',
    header: 'Owner',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.owner ?? '—',
  },
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? TICKET_LINK_CRITICALITY_LABELS[row.criticality] : '—',
  },
  {
    key: 'costCenter',
    header: 'Cost Center',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.costCenter ?? '—',
  },
  {
    key: 'notes',
    header: 'Notes',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.notes, 60),
  },
  {
    key: 'effectiveFrom',
    header: 'Effective From',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.effectiveFrom),
  },
];

export const ticketLinkDefaultVisible = ticketLinkColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
