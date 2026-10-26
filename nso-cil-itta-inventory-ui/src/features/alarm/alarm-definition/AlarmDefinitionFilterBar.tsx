import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { ALARM_DEFINITION_SEVERITY_OPTIONS } from './types';
import type { AlarmDefinitionFilter } from './types';

interface Props {
  value: AlarmDefinitionFilter;
  onChange: (next: AlarmDefinitionFilter) => void;
  onReset: () => void;
}

export function AlarmDefinitionFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: AlarmDefinitionFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Alarm Definition filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Alarm Definition"
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
      <FilterField label="Alarm Code">
        <Input
          aria-label="Alarm Code filter"
          value={value.alarmCode ?? ''}
          onChange={(v) => patch({ alarmCode: v || undefined })}
        />
      </FilterField>
      <FilterField label="Severity">
        <MultiSelect
          ariaLabel="Severity filter"
          options={ALARM_DEFINITION_SEVERITY_OPTIONS}
          value={value.severity ?? []}
          onChange={(v) => patch({ severity: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Probable Cause">
        <Input
          aria-label="Probable Cause filter"
          value={value.probableCause ?? ''}
          onChange={(v) => patch({ probableCause: v || undefined })}
        />
      </FilterField>
      <FilterField label="Auto Clear">
        <TriState
          ariaLabel="Auto Clear filter"
          value={value.autoClear}
          onChange={(v) => patch({ autoClear: v })}
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
