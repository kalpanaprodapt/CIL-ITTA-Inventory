import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { DISCOVERY_JOB_JOB_STATE_OPTIONS, DISCOVERY_JOB_CRITICALITY_OPTIONS } from './types';
import type { DiscoveryJobFilter } from './types';

interface Props {
  value: DiscoveryJobFilter;
  onChange: (next: DiscoveryJobFilter) => void;
  onReset: () => void;
}

export function DiscoveryJobFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: DiscoveryJobFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Discovery Job filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Discovery Job"
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
      <FilterField label="Profile Id">
        <Input
          aria-label="Profile Id filter"
          placeholder="UUID"
          value={value.profileId ?? ''}
          onChange={(v) => patch({ profileId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Started At">
        <DateRange
          ariaLabel="Started At"
          from={value.startedAtFrom}
          to={value.startedAtTo}
          onChange={(from, to) => patch({ startedAtFrom: from, startedAtTo: to })}
        />
      </FilterField>
      <FilterField label="Finished At">
        <DateRange
          ariaLabel="Finished At"
          from={value.finishedAtFrom}
          to={value.finishedAtTo}
          onChange={(from, to) => patch({ finishedAtFrom: from, finishedAtTo: to })}
        />
      </FilterField>
      <FilterField label="Job State">
        <MultiSelect
          ariaLabel="Job State filter"
          options={DISCOVERY_JOB_JOB_STATE_OPTIONS}
          value={value.jobState ?? []}
          onChange={(v) => patch({ jobState: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Assets Found">
        <RangeInputs
          ariaLabel="Assets Found"
          min={value.assetsFoundMin}
          max={value.assetsFoundMax}
          onChange={(min, max) => patch({ assetsFoundMin: min, assetsFoundMax: max })}
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
          options={DISCOVERY_JOB_CRITICALITY_OPTIONS}
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
