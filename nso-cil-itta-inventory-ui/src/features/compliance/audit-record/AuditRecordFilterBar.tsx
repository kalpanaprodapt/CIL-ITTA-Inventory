import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { AUDIT_RECORD_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { AuditRecordFilter } from './types';

interface Props {
  value: AuditRecordFilter;
  onChange: (next: AuditRecordFilter) => void;
  onReset: () => void;
}

export function AuditRecordFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: AuditRecordFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Audit Record filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Audit Record"
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
      <FilterField label="Actor">
        <Input
          aria-label="Actor filter"
          value={value.actor ?? ''}
          onChange={(v) => patch({ actor: v || undefined })}
        />
      </FilterField>
      <FilterField label="Action">
        <Input
          aria-label="Action filter"
          value={value.action ?? ''}
          onChange={(v) => patch({ action: v || undefined })}
        />
      </FilterField>
      <FilterField label="Entity Ref">
        <Input
          aria-label="Entity Ref filter"
          value={value.entityRef ?? ''}
          onChange={(v) => patch({ entityRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Occurred At">
        <DateRange
          ariaLabel="Occurred At"
          from={value.occurredAtFrom}
          to={value.occurredAtTo}
          onChange={(from, to) => patch({ occurredAtFrom: from, occurredAtTo: to })}
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
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
        />
      </FilterField>
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={AUDIT_RECORD_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
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
