import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { CHANGE_REQUEST_CHANGE_TYPE_OPTIONS, CHANGE_REQUEST_RISK_LEVEL_OPTIONS, CHANGE_REQUEST_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { ChangeRequestFilter } from './types';

interface Props {
  value: ChangeRequestFilter;
  onChange: (next: ChangeRequestFilter) => void;
  onReset: () => void;
}

export function ChangeRequestFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ChangeRequestFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Change Request filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Change Request"
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
      <FilterField label="Change Number">
        <Input
          aria-label="Change Number filter"
          value={value.changeNumber ?? ''}
          onChange={(v) => patch({ changeNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Change Type">
        <MultiSelect
          ariaLabel="Change Type filter"
          options={CHANGE_REQUEST_CHANGE_TYPE_OPTIONS}
          value={value.changeType ?? []}
          onChange={(v) => patch({ changeType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Risk Level">
        <MultiSelect
          ariaLabel="Risk Level filter"
          options={CHANGE_REQUEST_RISK_LEVEL_OPTIONS}
          value={value.riskLevel ?? []}
          onChange={(v) => patch({ riskLevel: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Planned Start">
        <DateRange
          ariaLabel="Planned Start"
          from={value.plannedStartFrom}
          to={value.plannedStartTo}
          onChange={(from, to) => patch({ plannedStartFrom: from, plannedStartTo: to })}
        />
      </FilterField>
      <FilterField label="Planned End">
        <DateRange
          ariaLabel="Planned End"
          from={value.plannedEndFrom}
          to={value.plannedEndTo}
          onChange={(from, to) => patch({ plannedEndFrom: from, plannedEndTo: to })}
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
          options={CHANGE_REQUEST_LIFECYCLE_STAGE_OPTIONS}
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
