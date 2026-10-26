import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SYNC_JOB_DIRECTION_OPTIONS, SYNC_JOB_LAST_STATUS_OPTIONS, SYNC_JOB_CRITICALITY_OPTIONS } from './types';
import type { SyncJobFilter } from './types';

interface Props {
  value: SyncJobFilter;
  onChange: (next: SyncJobFilter) => void;
  onReset: () => void;
}

export function SyncJobFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: SyncJobFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Sync Job filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Sync Job"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Endpoint Id">
        <Input
          aria-label="Endpoint Id filter"
          placeholder="UUID"
          value={value.endpointId ?? ''}
          onChange={(v) => patch({ endpointId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Direction">
        <MultiSelect
          ariaLabel="Direction filter"
          options={SYNC_JOB_DIRECTION_OPTIONS}
          value={value.direction ?? []}
          onChange={(v) => patch({ direction: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Schedule Cron">
        <Input
          aria-label="Schedule Cron filter"
          value={value.scheduleCron ?? ''}
          onChange={(v) => patch({ scheduleCron: v || undefined })}
        />
      </FilterField>
      <FilterField label="Last Run At">
        <DateRange
          ariaLabel="Last Run At"
          from={value.lastRunAtFrom}
          to={value.lastRunAtTo}
          onChange={(from, to) => patch({ lastRunAtFrom: from, lastRunAtTo: to })}
        />
      </FilterField>
      <FilterField label="Last Status">
        <MultiSelect
          ariaLabel="Last Status filter"
          options={SYNC_JOB_LAST_STATUS_OPTIONS}
          value={value.lastStatus ?? []}
          onChange={(v) => patch({ lastStatus: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={SYNC_JOB_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
        />
      </FilterField>
      <FilterField label="Effective From">
        <DateRange
          ariaLabel="Effective From"
          from={value.effectiveFromFrom}
          to={value.effectiveFromTo}
          onChange={(from, to) => patch({ effectiveFromFrom: from, effectiveFromTo: to })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
