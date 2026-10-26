import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';

import type { SyncMappingFilter } from './types';

interface Props {
  value: SyncMappingFilter;
  onChange: (next: SyncMappingFilter) => void;
  onReset: () => void;
}

export function SyncMappingFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: SyncMappingFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Sync Mapping filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Sync Mapping"
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
      <FilterField label="Job Id">
        <Input
          aria-label="Job Id filter"
          placeholder="UUID"
          value={value.jobId ?? ''}
          onChange={(v) => patch({ jobId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Source Field">
        <Input
          aria-label="Source Field filter"
          value={value.sourceField ?? ''}
          onChange={(v) => patch({ sourceField: v || undefined })}
        />
      </FilterField>
      <FilterField label="Target Field">
        <Input
          aria-label="Target Field filter"
          value={value.targetField ?? ''}
          onChange={(v) => patch({ targetField: v || undefined })}
        />
      </FilterField>
      <FilterField label="Transform">
        <Input
          aria-label="Transform filter"
          value={value.transform ?? ''}
          onChange={(v) => patch({ transform: v || undefined })}
        />
      </FilterField>
      <FilterField label="Required">
        <TriState
          ariaLabel="Required filter"
          value={value.required}
          onChange={(v) => patch({ required: v })}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
