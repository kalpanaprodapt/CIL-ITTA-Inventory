import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SITE_SITE_TYPE_OPTIONS, SITE_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { SiteFilter } from './types';

interface Props {
  value: SiteFilter;
  onChange: (next: SiteFilter) => void;
  onReset: () => void;
}

export function SiteFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: SiteFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Site filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Site"
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
      <FilterField label="City Id">
        <Input
          aria-label="City Id filter"
          placeholder="UUID"
          value={value.cityId ?? ''}
          onChange={(v) => patch({ cityId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Site Type">
        <MultiSelect
          ariaLabel="Site Type filter"
          options={SITE_SITE_TYPE_OPTIONS}
          value={value.siteType ?? []}
          onChange={(v) => patch({ siteType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Address">
        <Input
          aria-label="Address filter"
          value={value.address ?? ''}
          onChange={(v) => patch({ address: v || undefined })}
        />
      </FilterField>
      <FilterField label="Latitude">
        <RangeInputs
          ariaLabel="Latitude"
          min={value.latitudeMin}
          max={value.latitudeMax}
          onChange={(min, max) => patch({ latitudeMin: min, latitudeMax: max })}
        />
      </FilterField>
      <FilterField label="Longitude">
        <RangeInputs
          ariaLabel="Longitude"
          min={value.longitudeMin}
          max={value.longitudeMax}
          onChange={(min, max) => patch({ longitudeMin: min, longitudeMax: max })}
        />
      </FilterField>
      <FilterField label="Power Kw">
        <RangeInputs
          ariaLabel="Power Kw"
          min={value.powerKwMin}
          max={value.powerKwMax}
          onChange={(min, max) => patch({ powerKwMin: min, powerKwMax: max })}
        />
      </FilterField>
      <FilterField label="Access Hours">
        <Input
          aria-label="Access Hours filter"
          value={value.accessHours ?? ''}
          onChange={(v) => patch({ accessHours: v || undefined })}
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
          options={SITE_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
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
