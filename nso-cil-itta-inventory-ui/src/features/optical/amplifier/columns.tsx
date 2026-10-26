import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { AMPLIFIER_AMP_TYPE_LABELS, AMPLIFIER_LIFECYCLE_STAGE_LABELS, AMPLIFIER_CRITICALITY_LABELS } from './types';
import type { Amplifier } from './types';

export const amplifierColumns: Column<Amplifier>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/optical/amplifiers/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'systemId',
    header: 'System Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.systemId} />,
  },
  {
    key: 'ampType',
    header: 'Amp Type',
    sortable: true,
    align: 'left',
    render: (row) => row.ampType ? AMPLIFIER_AMP_TYPE_LABELS[row.ampType] : '—',
  },
  {
    key: 'gainDb',
    header: 'Gain Db',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.gainDb),
  },
  {
    key: 'outputPowerDbm',
    header: 'Output Power Dbm',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.outputPowerDbm),
  },
  {
    key: 'noiseFigureDb',
    header: 'Noise Figure Db',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatDecimal(row.noiseFigureDb),
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? AMPLIFIER_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? AMPLIFIER_CRITICALITY_LABELS[row.criticality] : '—',
  },
];

export const amplifierDefaultVisible = amplifierColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
