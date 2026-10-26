import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { PATCH_SEVERITY_OPTIONS, PATCH_CRITICALITY_OPTIONS } from './types';
import type { PatchFilter } from './types';

interface Props {
  value: PatchFilter;
  onChange: (next: PatchFilter) => void;
  onReset: () => void;
}

export function PatchFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: PatchFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Patch filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Patch"
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
      <FilterField label="Version Id">
        <Input
          aria-label="Version Id filter"
          placeholder="UUID"
          value={value.versionId ?? ''}
          onChange={(v) => patch({ versionId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Patch Id">
        <Input
          aria-label="Patch Id filter"
          value={value.patchId ?? ''}
          onChange={(v) => patch({ patchId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Severity">
        <MultiSelect
          ariaLabel="Severity filter"
          options={PATCH_SEVERITY_OPTIONS}
          value={value.severity ?? []}
          onChange={(v) => patch({ severity: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Released On">
        <DateRange
          ariaLabel="Released On"
          from={value.releasedOnFrom}
          to={value.releasedOnTo}
          onChange={(from, to) => patch({ releasedOnFrom: from, releasedOnTo: to })}
        />
      </FilterField>
      <FilterField label="Reboot Required">
        <TriState
          ariaLabel="Reboot Required filter"
          value={value.rebootRequired}
          onChange={(v) => patch({ rebootRequired: v })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={PATCH_CRITICALITY_OPTIONS}
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
