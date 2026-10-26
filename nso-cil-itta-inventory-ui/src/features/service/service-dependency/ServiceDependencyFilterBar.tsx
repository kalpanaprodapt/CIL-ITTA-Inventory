import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SERVICE_DEPENDENCY_DEPENDENCY_TYPE_OPTIONS, SERVICE_DEPENDENCY_CRITICALITY_OPTIONS } from './types';
import type { ServiceDependencyFilter } from './types';

interface Props {
  value: ServiceDependencyFilter;
  onChange: (next: ServiceDependencyFilter) => void;
  onReset: () => void;
}

export function ServiceDependencyFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ServiceDependencyFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Service Dependency filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Service Dependency"
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
      <FilterField label="Service Id">
        <Input
          aria-label="Service Id filter"
          placeholder="UUID"
          value={value.serviceId ?? ''}
          onChange={(v) => patch({ serviceId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Depends On Id">
        <Input
          aria-label="Depends On Id filter"
          placeholder="UUID"
          value={value.dependsOnId ?? ''}
          onChange={(v) => patch({ dependsOnId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Dependency Type">
        <MultiSelect
          ariaLabel="Dependency Type filter"
          options={SERVICE_DEPENDENCY_DEPENDENCY_TYPE_OPTIONS}
          value={value.dependencyType ?? []}
          onChange={(v) => patch({ dependencyType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Impact Score">
        <RangeInputs
          ariaLabel="Impact Score"
          min={value.impactScoreMin}
          max={value.impactScoreMax}
          onChange={(min, max) => patch({ impactScoreMin: min, impactScoreMax: max })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={SERVICE_DEPENDENCY_CRITICALITY_OPTIONS}
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
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
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
